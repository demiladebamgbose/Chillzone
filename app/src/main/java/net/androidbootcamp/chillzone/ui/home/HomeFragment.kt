package net.androidbootcamp.chillzone.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import net.androidbootcamp.chillzone.BR
import net.androidbootcamp.chillzone.ChillApp
import net.androidbootcamp.chillzone.R
import net.androidbootcamp.chillzone.databinding.FragmentHomeBinding
import net.androidbootcamp.chillzone.retrofit.model.Movie
import net.androidbootcamp.chillzone.viewModels.VMFactory
import java.lang.Exception
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
        var movies : MutableList<Movie>?

        var homeFragmentScope = CoroutineScope(Dispatchers.Main)
        try{
            homeFragmentScope.launch {
                movies = homeViewModel.discoverMovies(getString(R.string.apiKey)).await().results
                homeViewModel.num.value = movies?.size.toString()
            }

        } catch (err:Exception) {
           err.printStackTrace()
        }
        return homeBinding.getRoot()
    }
}