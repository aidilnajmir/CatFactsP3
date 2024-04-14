package com.cis436.p3

import android.R
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cis436.p3.databinding.FragmentCatListBinding
import org.json.JSONArray
import org.json.JSONObject

class CatListFragment : Fragment() {

    // Define a cat class to store fetched data before saving to view model
    data class Cat(
        val name: String,
        val temperament: String,
        val origin: String,
        val referenceImageId: String,
        var imageUrl: String
    )

    private var _binding: FragmentCatListBinding? = null
    private val binding get() = _binding!!

    // Use imported spinner and CatViewModel
    private lateinit var spinner: Spinner
    private lateinit var viewModel: CatViewModel

    // Create an array of cat to store fetched cats' data
    private val cats: MutableList<Cat> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatListBinding.inflate(inflater, container, false)
        spinner = binding.catSpinner // Bind the Spinner component to the defined spinner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CatViewModel::class.java) // Define viewModel based on the CatViewModel
        populateSpinner() // Call the method to populate the spinner to a cat list fetched using API call
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // method to get cat list through API
    private fun populateSpinner() {
        // Define url to get cat data
        var catUrl = "https://api.thecatapi.com/v1/breeds" + 
        "?api_key=YOUR_API_KEY_HERE"

        val queue = Volley.newRequestQueue(requireContext())

        // Make request through GET method of API call to get cat data
        val stringRequest = StringRequest(
            Request.Method.GET, catUrl,
            { response ->
                val catsArray: JSONArray = JSONArray(response) // Store the response as a JSON array
                // For each cat, add the cat info to the defined cat array
                for (i in 0 until catsArray.length()) {
                    val theCat: JSONObject = catsArray.getJSONObject(i)
                    val cat = Cat(
                        theCat.getString("name"),
                        theCat.getString("temperament"),
                        theCat.getString("origin"),
                        // If the cat has no image, assign an empty string to the attribute
                        if (theCat.has("reference_image_id")) theCat.getString("reference_image_id") else "",
                        "" // image url will be fetched later, so it is an empty string as placeholder for now
                    )
                    cats.add(cat)
                }
                val adapter = ArrayAdapter(
                    requireContext(),
                    R.layout.simple_spinner_item,
                    cats.map { it.name }
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            },
            {
                Log.e("CatListFragment", "Failed to fetch cat names")
            })
        queue.add(stringRequest)

        // call the function to handle spinner item selection
        setupSpinnerListener()
    }

    // method to handle the cat selection
    private fun setupSpinnerListener() {
        viewModel = ViewModelProvider(requireActivity())[CatViewModel::class.java]
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCat = cats[position]

                // If the selected cat has an image, make another API call to get the image
                if (selectedCat.referenceImageId.isNotEmpty()) {
                    val catImageUrl =
                        "https://api.thecatapi.com/v1/images/${selectedCat.referenceImageId}" +
                                "?api_key=YOUR_API_KEY_HERE"

                    val queue = Volley.newRequestQueue(requireContext())

                    val stringRequest = StringRequest(
                        Request.Method.GET, catImageUrl,
                        { response ->
                            // Load the image url and save it to the cat array at the position of selected cat
                            val catImage: JSONObject = JSONObject(response)
                            val imageUrl = catImage.getString("url")
                            selectedCat.imageUrl = imageUrl

                            // Set the selected cat info in the ViewModel after setting the image URL
                            viewModel.setSelectedCatInfo(
                                selectedCat.name,
                                selectedCat.temperament,
                                selectedCat.origin,
                                selectedCat.referenceImageId,
                                selectedCat.imageUrl
                            )
                        },
                        {
                            Log.e("CatListFragment", "Failed to fetch cat image")
                        })
                    queue.add(stringRequest)
                }
                // If the selected cat has no image, set the selected cat with empty image url string
                else {
                    viewModel.setSelectedCatInfo(
                        selectedCat.name,
                        selectedCat.temperament,
                        selectedCat.origin,
                        selectedCat.referenceImageId,
                        selectedCat.imageUrl
                    )
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }
}
