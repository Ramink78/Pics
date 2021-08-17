package pics.app.ui.base

import androidx.annotation.StringRes
import pics.app.data.photo.model.Photo

sealed class DetailRow{

    data class HeaderPhoto(val photo:Photo):DetailRow()
    data class Separator(@StringRes val title:Int):DetailRow()
    data class Detail(val primaryText:String,@StringRes val secondaryText:Int):DetailRow()

}
