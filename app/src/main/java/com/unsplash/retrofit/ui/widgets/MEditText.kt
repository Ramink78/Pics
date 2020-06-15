package com.unsplash.retrofit.ui.widgets

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.unsplash.retrofit.R

class MEditText : AppCompatEditText {
    var paint: Paint? = null
    var arcPaint: Paint? = null
    var rect: Rect? = null


    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        this.hint = "Search"
        this.setHintTextColor(resources.getColor(R.color.searchBarHintColor, null))
        rect = Rect()
        rect.apply {
            this?.bottom = bottom
            this?.top = top
            this?.left = left
            this?.right = right

        }
        paint = Paint()
        arcPaint = Paint()
        paint.apply {
            this?.color = resources.getColor(R.color.searchBarColor, null)
            this?.style = Paint.Style.FILL
            this?.isAntiAlias = true

        }
        /*  arcPaint.apply {
              this?.color= Color.WHITE
              this?.style=Paint.Style.STROKE
              this?.isAntiAlias=true
          }*/


    }

    fun setTheme() {

    }

    override fun onDraw(canvas: Canvas?) {
        var height = height
        var width = width
        canvas?.drawRoundRect(
            left.toFloat(),
            top.toFloat(),
            right.toFloat(),
            height.toFloat(),
            height / 2f,
            height / 2f,
            paint!!
        )


        super.onDraw(canvas)


    }
}