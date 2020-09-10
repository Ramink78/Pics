package pics.app.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import pics.app.R
import pics.app.ServiceBuilder
import pics.app.adapters.ExploreAdapter
import pics.app.adapters.OnPhotoClickListener
import pics.app.data.details.model.Photo
import pics.app.data.photo.PhotoAPI
import pics.app.network.NetworkState
import pics.app.repo.explore.ExploreRepo
import kotlinx.android.synthetic.main.fragment_explore.*


class ExploreFragment : Fragment() {

    private lateinit var exploreViewModel: ExploreViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutm: GridLayoutManager
    private lateinit var navController: NavController
    private lateinit var exploreAdapter: ExploreAdapter
    private lateinit var exploreRepo: ExploreRepo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reenterTransition =  TransitionInflater.from(context).inflateTransition(R.transition.shared_element_transition2)
        exploreRepo = ExploreRepo(ServiceBuilder.buildService(PhotoAPI::class.java))
        exploreViewModel = getViewModel()



        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutm = GridLayoutManager(requireContext(), 3)
        exploreAdapter = ExploreAdapter(requireContext())
        navController = Navigation.findNavController(view)
        recyclerView = view.findViewById(R.id.explore_recyceler)
        recyclerView.apply {
            layoutManager = layoutm
        }

        recyclerView.adapter = exploreAdapter
        exploreViewModel.randomPhotos.observe(viewLifecycleOwner, Observer {
            exploreAdapter.submitList(it)
        })
        exploreViewModel.networkState.observe(viewLifecycleOwner, Observer {
            when(it){
                NetworkState.INITIALIZING->explore_progress_bar.visibility=View.VISIBLE
                else->explore_progress_bar.visibility=View.GONE
            }

        })
        exploreAdapter.setOnPhotoClickListener(object : OnPhotoClickListener {
            override fun onClick(id: String?, position: Int, view: View, photo: Photo?) {
                val action =
                    photo.let {
                        ExploreFragmentDirections.actionNavigationExploreToDetailOfImage(
                            photo = it!!
                        )
                    }
                val extras = FragmentNavigatorExtras(
                    view to (id ?: "no id")
                )

                navController.navigate(action, extras)
            }

        })


    }

    private fun getViewModel(): ExploreViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("cast eception")
                return ExploreViewModel(exploreRepo) as T
            }
        })[ExploreViewModel::class.java]
    }

}