package pics.app.ui.collections

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import pics.app.PicsApp
import pics.app.adapters.CollectionsAdapter
import pics.app.adapters.OnCollectionClickListener
import pics.app.data.collections.model.Collection
import pics.app.databinding.FragmentCollectionsBinding
import pics.app.ui.base.BasePhotoListFragment
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Inject


class CollectionsFragment : BasePhotoListFragment<Collection, RecyclerView.ViewHolder>() {

    @Inject
    lateinit var collectionsViewModel: CollectionsViewModel
    private lateinit var navController: NavController

    @Inject
    lateinit var collectionsAdapter: CollectionsAdapter
    lateinit var binding: FragmentCollectionsBinding

    @Inject
    lateinit var retrofit: Retrofit


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)


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
                Timber.d("first")
                lifecycleScope.launch {
                    listAdapter.submitData(it)
                }

            }
            collectionClick.observe(viewLifecycleOwner){
                Timber.d("observed")

            }


        }
        listAdapter.apply {
            addLoadStateListener {
                when (it.refresh) {
                    LoadState.Loading -> showLoading()
                    else -> showSuccess()
                }
            }
            setOnPhotoClickListener(object : OnCollectionClickListener{
                override fun onClick(id: String, collection: Collection) {
                     val action =
                    CollectionsFragmentDirections.actionNavigationCollectionsToPhotosCollection(collection)
                navController.navigate(action)
                }

            })
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as PicsApp).appComponent.inject(this)
    }

    override val listAdapter
        get() = collectionsAdapter
    override val itemSpace = 0
    override val spanCount: Int
        get() = 1


}