package com.unsplash.retrofit.data

import android.view.View
import com.unsplash.retrofit.ui.widgets.AspectRatioImageView

fun View.toTransitionGroup() =    this to transitionName
private fun AspectRatioImageView.setAspectRatio(width: Int?, height: Int?) {
    if (width != null && height != null) {
        aspectRatio = height.toDouble() / width.toDouble()
    }
}