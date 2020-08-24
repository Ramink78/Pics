package com.unsplash.retrofit.ui.collections

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.unsplash.retrofit.R
import com.unsplash.retrofit.adapters.CollectionPhotosAdapter
import com.unsplash.retrofit.adapters.OnLoadMoreListener
import com.unsplash.retrofit.data.details.Photo
import com.unsplash.retrofit.databinding.CDetailsBinding
import com.unsplash.retrofit.ui.recyclerview.ItemSpacing
import com.unsplash.retrofit.ui.widgets.AspectRatioImageView

class CollectionPhotosFragment : Fragment() {
    private lateinit var viewmodel: CollectionPhotosViewModel
    lateinit var navController: NavController
    private var page = 1
    lateinit var binding: CDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewmodel = ViewModelProvider(this).get(CollectionPhotosViewModel::class.java)
        binding = CDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    val args: CollectionPhotosFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            navController = Navigation.findNavController(view)
            val myadapter = CollectionPhotosAdapter(requireContext())
            binding.rvCollectionPhotos.apply {
                adapter = myadapter
                addItemDecoration(
                    ItemSpacing(
                        resources.getDimensionPixelSize(R.dimen.item_space),
                        2
                    )
                )
            }
            myadapter.photoSelectedListener =
                object : CollectionPhotosAdapter.PhotoSelectedListener {
                    override fun onPhotoSelected(photo: Photo, imageView: AspectRatioImageView) {

                        val action =
                            CollectionPhotosFragmentDirections.actionCollectionPhotosFragmentToDetailOfImage(
                                photo
                            )
                        val extras = FragmentNavigatorExtras(
                            imageView to photo.id
                        )
                        findNavController().navigate(action, extras)
                    }

                }
            viewmodel.getCollectionPhotos(args.id, page)

            viewmodel.photos.observe(viewLifecycleOwner, Observer {
                myadapter.addItems(it)


            })
            myadapter.setOnLoadMoreListener(object : OnLoadMoreListener {
                override fun onLoadMoreData() {
                    viewmodel.getCollectionPhotos(args.id, page++)

                }

            })


    }

}