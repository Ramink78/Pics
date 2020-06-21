package com.unsplash.retrofit.ui.widgets

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewGroupCompat
import com.unsplash.retrofit.R
import kotlinx.android.synthetic.main.headr_of_detail_image.view.*

class DetailsWidget : ViewGroup {
    var primaryText: String? = null
        get() = field
        set(value) {
            field = value
        }
    var seconderyText: String? = null
        get() = field
        set(value) {
            field = value
        }
    var icon: Int? = null
        set(value) {
            field = value
        }
    val bgPaint: Paint = Paint()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    private fun init() {
        bgPaint.color = Color.BLUE
        bgPaint.style = Paint.Style.FILL
        bgPaint.isAntiAlias = true
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        TODO("Not yet implemented")
    }


}