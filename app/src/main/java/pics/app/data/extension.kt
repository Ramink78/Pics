package pics.app.data

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.imageview.ShapeableImageView
import pics.app.R
import pics.app.data.photo.model.Photo
import pics.app.ui.widgets.AspectRatioImageView
import timber.log.Timber
import kotlin.math.ceil


@BindingAdapter("width", "height", requireAll = true)
fun setAspectRatio(imageView: AspectRatioImageView, width: Int, height: Int) {
    Timber.d("width is $width , height is $height")
    imageView.aspectRatio = height.toDouble() / width.toDouble()

}

@BindingAdapter("photo")
fun loadPhoto(imageView: ShapeableImageView, photo: Photo) {

    imageView.apply {
        background = ColorDrawable(Color.parseColor(photo.color))
    }
    Glide.with(imageView)
        .load(photo.urls.small)
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

@BindingAdapter("profilePhoto")
fun loadProfile(imageView: ShapeableImageView, url: String) {
    imageView.apply {

    }
    Glide.with(imageView)
        .load(url)
        .placeholder(R.drawable.ic_avatar)
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

fun AspectRatioImageView.setAspectRatio(width: Int?, height: Int?) {
    if (width != null && height != null) {
        aspectRatio = height.toDouble() / width.toDouble()
    }
}

fun getScreenWidth(): Int {
    return Resources.getSystem().displayMetrics.widthPixels
}