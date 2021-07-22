package pics.app.ui.collections

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import pics.app.PicsApp
import pics.app.adapters.CollectionsAdapter
import pics.app.databinding.FragmentCollectionsBinding
import retrofit2.Retrofit
import javax.inject.Inject


class CollectionsFragment : Fragment() {

    @Inject
    lateinit var collectionsViewModel: CollectionsViewModel
    private lateinit var layoutm: LinearLayoutManager
    private lateinit var navController: NavController
    @Inject
    lateinit var collectionsAdapter: CollectionsAdapter
    lateinit var binding: FragmentCollectionsBinding

    @Inject
    lateinit var retrofit: Retrofit


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutm = LinearLayoutManager(requireContext())
        navController = Navigation.findNavController(view)
        binding.recyclerViewCollection.apply {
            layoutManager = layoutm
            adapter = collectionsAdapter
        }

     /*   collectionsAdapter.setOnPhotoClickListener(object : OnCollectionClickListener {
            override fun onClick(id: Int?, position: Int, view: View, photo: Collection?) {
                /*  val action =
                      photo.let {
                          CollectionsFragmentDirections.actionNavigationExploreToDetailOfImage(
                              photo = it!!
                          )
                      }
                  val extras = FragmentNavigatorExtras(
                      view to (id ?: "no id")
                  )

                  navController.navigate(action, extras)*/
            }

        })*/
        collectionsViewModel.apply {
            collections.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    collectionsAdapter.submitData(it)
                }

            }
        }
        collectionsAdapter.apply {
            addLoadStateListener {
                binding.recyclerViewCollection.isVisible= it.refresh is LoadState.NotLoading
            }
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as PicsApp).appComponent.inject(this)
    }


}