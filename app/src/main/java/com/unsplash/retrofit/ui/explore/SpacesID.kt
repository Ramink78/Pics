package com.unsplash.retrofit.ui.explore

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacesID(var space:Int):RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = space;

        outRect.bottom = space;
        if (parent.getChildAdapterPosition(view)%2==0){
            outRect.right = 0;
        }else{
            outRect.right = space;

        }

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) ==1) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}
