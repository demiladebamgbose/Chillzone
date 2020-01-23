package net.androidbootcamp.chillzone.ui.home

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
import net.androidbootcamp.chillzone.R.*
import net.androidbootcamp.chillzone.databinding.FragmentGalleryBinding
import net.androidbootcamp.chillzone.databinding.FragmentHomeBinding
import net.androidbootcamp.chillzone.viewModels.VMFactory
import javax.inject.Inject

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    @Inject
    lateinit var vmFactory: VMFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity?.getApplication() as ChillApp).appComponent.inject(this)

        homeViewModel =
            ViewModelProviders.of(this, vmFactory).get(HomeViewModel::class.java)
        val homeBinding: FragmentHomeBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home, container, false)
        homeBinding.state = homeViewModel
        homeBinding.lifecycleOwner = this
        return homeBinding.getRoot()
    }
}