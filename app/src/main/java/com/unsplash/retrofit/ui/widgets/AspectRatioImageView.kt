package com.unsplash.retrofit.ui.widgets

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.imageview.ShapeableImageView

class AspectRatioImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ShapeableImageView(context, attrs, defStyleAttr) {

    var aspectRatio: Double = -1.0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (aspectRatio == -1.0) return

        val width = measuredWidth
        val height = (width* aspectRatio).toInt()
        if (height == measuredHeight) return
        setMeasuredDimension(width, height)
    }
}