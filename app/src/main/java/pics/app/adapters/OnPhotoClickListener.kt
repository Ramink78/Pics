package pics.app.adapters

import android.view.View
import pics.app.data.details.model.Photo

interface OnPhotoClickListener {
    fun onClick(id: String?, position: Int, view: View, photo: Photo?)

}