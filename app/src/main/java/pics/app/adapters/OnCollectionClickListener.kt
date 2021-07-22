package pics.app.adapters

import android.view.View
import pics.app.data.collections.model.Collection
import pics.app.data.photo.model.Photo

interface OnCollectionClickListener {
    fun onClick(id: Int?, position: Int, view: View, photo: Collection?)

}