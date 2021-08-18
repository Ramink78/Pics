package pics.app.ui.base

import androidx.annotation.StringRes
import androidx.collection.ArrayMap
import pics.app.data.photo.model.Photo

sealed class DetailRow {

    data class HeaderPhoto(val photo: Photo) : DetailRow()
    data class Category(@StringRes val title: Int) :
        DetailRow()

    data class Child(
        val primaryText: String,
       @StringRes  val secondaryText: Int
    ) : DetailRow()

}
