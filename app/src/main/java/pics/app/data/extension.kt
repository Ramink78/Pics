package pics.app.data

import android.view.View
import pics.app.ui.widgets.AspectRatioImageView

fun View.toTransitionGroup() = this to transitionName
 fun AspectRatioImageView.setAspectRatio(width: Int?, height: Int?) {
    if (width != null && height != null) {
        aspectRatio = height.toDouble() / width.toDouble()
    }
}