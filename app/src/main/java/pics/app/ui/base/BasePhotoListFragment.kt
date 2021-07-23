package pics.app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import pics.app.data.dp
import pics.app.databinding.BasePhotoListFragmentBinding
import pics.app.utils.ItemSpacing

abstract class BasePhotoListFragment<T: Any,V:RecyclerView.ViewHolder> : Fragment() {

    private  var _binding:BasePhotoListFragmentBinding?= null
    private val binding get() = _binding!!
    abstract val listAdapter:BasePhotoListAdapter<T,V>
    abstract val lManager:RecyclerView.LayoutManager
    abstract val itemSpace:Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=BasePhotoListFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.baseRecyclerView.apply {
            adapter=listAdapter
            layoutManager=lManager
            addItemDecoration(ItemSpacing(itemSpace,2))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
    fun showLoading(){
        binding.baseRecyclerView.isVisible=false
        binding.swipeRefresh.isRefreshing=true
    }
    fun showSuccess(){
        binding.baseRecyclerView.isVisible=true
        binding.swipeRefresh.isRefreshing=false
    }






}
