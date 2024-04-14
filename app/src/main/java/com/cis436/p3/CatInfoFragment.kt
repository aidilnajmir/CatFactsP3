package com.cis436.p3

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cis436.p3.databinding.FragmentCatInfoBinding
import com.squareup.picasso.Picasso

class CatInfoFragment : Fragment() {

    // Data binding and ViewModel variables
    private var _binding: FragmentCatInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatInfoBinding.inflate(inflater, container, false)
        return binding.root
    } //end of onCreateView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // ViewModel initialization associated with the parent activity
        viewModel = ViewModelProvider(requireActivity())[CatViewModel::class.java]

        // Function call to observe changes in ViewModel data
        observeViewModel()
    } //end of onViewCreated

    private fun observeViewModel() {
        // Observe changes in selectedCatName LiveData and update the TextView
        viewModel.selectedCatName.observe(viewLifecycleOwner) { name ->
            binding.tvName.text = name
        }
        // Observe changes in selectedCatTemperament LiveData and update the TextView
        viewModel.selectedCatTemperament.observe(viewLifecycleOwner) { temperament ->
            binding.tvTemperament.text = temperament
        }
        // Observe changes in selectedCatOrigin LiveData and update the TextView
        viewModel.selectedCatOrigin.observe(viewLifecycleOwner) { origin ->
            binding.tvOrigin.text = origin
        }
        // Observe changes in selectedCatImageUrl LiveData and update the ImageView using Picasso
        viewModel.selectedCatImageUrl.observe(viewLifecycleOwner) { imageUrl ->
            if (!imageUrl.isNullOrEmpty()) {
                Picasso.get().load(imageUrl).into(binding.imgCat)
            } else {
                // Load a default image into the ImageView if imageUrl is null or empty
                Picasso.get().load(R.drawable.default_image).into(binding.imgCat)
            }
        }
    } //end if observeViewModel

    override fun onDestroyView() {
        super.onDestroyView()
        // Clean up the binding object to avoid memory leaks
        _binding = null
    } //end of onDestroyView
}

