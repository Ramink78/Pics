package pics.app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pics.app.R
import pics.app.databinding.BasePhotoListFragmentBinding

abstract class BasePhotoListFragment<T: Any,V:RecyclerView.ViewHolder> : Fragment() {
    lateinit var binding:BasePhotoListFragmentBinding

    abstract val photoListAdapter:BasePhotoListAdapter<T,V>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= BasePhotoListFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //// this is clean branch
        binding.baseRecyclerView.apply {
            layoutManager=LinearLayoutManager(requireContext())
            adapter=photoListAdapter
        }
    }
    fun showLoading(){
        binding.baseRecyclerView.isVisible=false
        binding.swiperefresh.isRefreshing=true
    }
    fun showSuccess(){
        binding.baseRecyclerView.isVisible=true
        binding.swiperefresh.isRefreshing=false
    }




}
