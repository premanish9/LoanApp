package com.example.loanapp.ui.prelogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.loanapp.R
import com.example.loanapp.databinding.FragmentGalleryBinding
import com.example.loanapp.utils.NavigationUtils
import com.example.loanapp.utils.StorageUtils

class PreLoginFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(PreLoginFragmentViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textHome
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

       /* if (StorageUtils.fetchString("mobile","")!=null){
            val bundle=Bundle()
            bundle.putBoolean("isSignIn",true)
            NavigationUtils.goToFragment(activity=requireActivity(),bundle=bundle, navigateToId = R.id.action_nav_gallery_to_nav_slideshow)

        }*/

        binding.signInButton.setOnClickListener {
            if (StorageUtils.fetchString("mobile","")!=null) {
                val bundle = Bundle()
                bundle.putBoolean("isSignIn", true)
                NavigationUtils.goToFragment(
                    activity = requireActivity(),
                    bundle = bundle,
                    navigateToId = R.id.action_nav_gallery_to_nav_slideshow
                )
            }else {
                val bundle = Bundle()
                bundle.putBoolean("isSignIn", true)
                NavigationUtils.goToFragment(
                    activity = requireActivity(),
                    bundle = bundle,
                    navigateToId = R.id.action_nav_gallery_to_nav_home
                )
            }
        }

        binding.submitButton.setOnClickListener {
            val bundle=Bundle()
            bundle.putBoolean("isSignIn",false)
            NavigationUtils.goToFragment(activity=requireActivity(),bundle=bundle, navigateToId = R.id.action_nav_gallery_to_nav_home)

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}