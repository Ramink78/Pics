package com.unsplash.retrofit.ui.collections

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.*
import com.unsplash.retrofit.R
import com.unsplash.retrofit.adapters.CollectionPhotosAdapter
import com.unsplash.retrofit.adapters.OnLoadMoreListener
import com.unsplash.retrofit.adapters.OnPhotoClickListener
import com.unsplash.retrofit.databinding.CDetailsBinding
import com.unsplash.retrofit.ui.explore.DetailPhotoArgs
import com.unsplash.retrofit.ui.explore.ExploreFragmentDirections
import com.unsplash.retrofit.ui.recyclerview.ItemSpacing
import kotlinx.android.synthetic.main.c_details.*
import kotlinx.android.synthetic.main.home.*

class CollectionPhotosFragment:Fragment() {
    private lateinit var viewmodel:CollectionPhotosViewModel
    lateinit var navController: NavController
    private var page=1
    lateinit var binding: CDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewmodel=ViewModelProvider(this).get(CollectionPhotosViewModel::class.java)
        binding=CDetailsBinding.inflate(inflater,container,false)
        return  binding.root
    }
    val args: CollectionPhotosFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController=Navigation.findNavController(view)
       val myadapter=CollectionPhotosAdapter(requireContext())
        binding.rvCollectionPhotos.apply {
            adapter=myadapter
            addItemDecoration(ItemSpacing(resources.getDimensionPixelSize(R.dimen.item_space),2))
        }
        myadapter.setOnPhotoClickListener(object: OnPhotoClickListener{
            override fun onClick(id: String?, position: Int,view: View) {
               val action= id?.let {
                    CollectionPhotosFragmentDirections.actionCollectionPhotosFragmentToDetailOfImage(
                        it
                    )
                }
                if (action != null) {
                    val extras= FragmentNavigatorExtras(


                    )


                    navController.navigate(action)
                }
                postponeEnterTransition()
                binding.rvCollectionPhotos.doOnPreDraw { startPostponedEnterTransition() }

                //1

                val extraInfoForSharedElement = FragmentNavigatorExtras(
                    view to "image"
                )
//3
                navigate(CollectionPhotosFragmentDirections.actionCollectionPhotosFragmentToDetailOfImage(), extraInfoForSharedElement)

            }

        })

        viewmodel.getCollectionPhotos(args.id,page)

        viewmodel.photos.observe(viewLifecycleOwner, Observer {
            myadapter.addItems(it)


        })
        myadapter.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMoreData() {
                viewmodel.getCollectionPhotos(args.id,page++)

            }

        })

    }
    private fun navigate(destination: NavDirections, extraInfo: FragmentNavigator.Extras) = with(findNavController()) {
        currentDestination?.getAction(destination.actionId)
            ?.let {
                navigate(destination, extraInfo) //2 }
            }
    }

}