package net.androidbootcamp.chillzone.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.androidbootcamp.chillzone.R
import net.androidbootcamp.chillzone.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProviders.of(this).get(GalleryViewModel::class.java)
        val binding : FragmentGalleryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)
        binding.state = galleryViewModel
        binding.lifecycleOwner = this
        val root = binding.getRoot();
        return root
    }
}