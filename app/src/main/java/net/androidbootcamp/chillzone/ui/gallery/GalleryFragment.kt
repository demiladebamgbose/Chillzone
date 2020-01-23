package net.androidbootcamp.chillzone.ui.gallery

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.androidbootcamp.chillzone.ChillApp
import net.androidbootcamp.chillzone.R
import net.androidbootcamp.chillzone.databinding.FragmentGalleryBinding
import net.androidbootcamp.chillzone.viewModels.VMFactory
import javax.inject.Inject

class GalleryFragment : Fragment() {

    @Inject
    lateinit var vmfactory : VMFactory
    private lateinit var galleryViewModel: GalleryViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity?.getApplication() as ChillApp).appComponent.inject(this)

        galleryViewModel =
            ViewModelProviders.of(this, vmfactory).get(GalleryViewModel::class.java)

        val binding : FragmentGalleryBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)

        binding.state = galleryViewModel
        binding.lifecycleOwner = this
        val root = binding.getRoot();
        return root
    }
}