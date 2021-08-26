package pics.app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import pics.app.adapters.ListStateAdapter
import pics.app.databinding.BasePhotoListFragmentBinding
import pics.app.utils.ItemSpacing

abstract class BasePhotoListFragment : Fragment() {
    lateinit var navController: NavController

    private var _binding: BasePhotoListFragmentBinding? = null
    val binding get() = _binding!!
    abstract val listAdapter: BasePhotoListAdapter
    abstract val itemSpace: Int
    abstract val spanCount: Int

    private lateinit var layoutManager: StaggeredGridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BasePhotoListFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        layoutManager = StaggeredGridLayoutManager(spanCount, RecyclerView.VERTICAL)
        //    layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.apply {
            baseRecyclerView.apply {
                adapter = listAdapter.withLoadStateFooter(footer = ListStateAdapter {
                    listAdapter.retry()
                })



                layoutManager = this@BasePhotoListFragment.layoutManager
                addItemDecoration(ItemSpacing(itemSpace, spanCount))
            }
            swipeRefresh.setOnRefreshListener {
                listAdapter.refresh()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun showLoading() {
        binding.baseRecyclerView.isVisible = false
        binding.swipeRefresh.isRefreshing = true
    }

    fun showSuccess() {
        binding.baseRecyclerView.isVisible = true
        binding.swipeRefresh.isRefreshing = false
    }


}
