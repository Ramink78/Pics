package pics.app.data

import android.view.View
import pics.app.ui.widgets.AspectRatioImageView
import android.content.res.Resources
import kotlin.math.ceil
fun View.toTransitionGroup() = this to transitionName
 fun AspectRatioImageView.setAspectRatio(width: Int?, height: Int?) {
    if (width != null && height != null) {
        aspectRatio = height.toDouble() / width.toDouble()
    }
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
