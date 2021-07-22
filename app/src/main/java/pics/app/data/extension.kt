package pics.app.data

import android.view.View
import pics.app.ui.widgets.AspectRatioImageView
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.imageview.ShapeableImageView
import pics.app.data.photo.model.Photo
import timber.log.Timber
import kotlin.math.ceil
@BindingAdapter("width","height",requireAll = true)
 fun setAspectRatio(imageView: AspectRatioImageView,width:Int,height:Int) {
    Timber.d("width is $width , height is $height")
        imageView.aspectRatio = height.toDouble()/width.toDouble()

}
@BindingAdapter("photo")
fun loadPhoto(imageView: AspectRatioImageView,photo: Photo){
    Glide.with(imageView)
        .load(photo.urls.small)
        .placeholder(ColorDrawable(Color.parseColor(photo.color)))
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}
@BindingAdapter("profilePhoto")
fun loadProfile(imageView: ShapeableImageView,url: String){
    Glide.with(imageView)
        .load(url)
        .placeholder(ColorDrawable(Color.WHITE))
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}
fun Int.dpf(): Float {
    return this.dp().toFloat()
}

fun Float.dpf(): Float {
    return this.dp().toFloat()
}

fun Int.dp(): Int {
    return if (this == 0) {
        0
    } else ceil((Resources.getSystem().displayMetrics.density * this).toDouble()).toInt()
}

fun Float.dp(): Int {

    return if (this == 0f) {
        0
    } else ceil((Resources.getSystem().displayMetrics.density * this).toDouble()).toInt()
}
fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}
