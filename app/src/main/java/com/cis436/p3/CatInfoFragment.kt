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

    private var _binding: FragmentCatInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CatViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.selectedCatName.observe(viewLifecycleOwner) { name ->
            binding.tvName.text = name
        }
        viewModel.selectedCatTemperament.observe(viewLifecycleOwner) { temperament ->
            binding.tvTemperament.text = temperament
        }
        viewModel.selectedCatOrigin.observe(viewLifecycleOwner) { origin ->
            binding.tvOrigin.text = origin
        }
        viewModel.selectedCatImageUrl.observe(viewLifecycleOwner) { imageUrl ->
            //Log.d("Image", imageUrl)
            if (!imageUrl.isNullOrEmpty()) {
                Picasso.get().load(imageUrl).into(binding.imgCat)
            } else {
                Picasso.get().load(R.drawable.default_image).into(binding.imgCat)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

