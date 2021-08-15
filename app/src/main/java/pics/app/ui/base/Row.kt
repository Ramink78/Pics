package pics.app.ui.base

import pics.app.data.TITLE_TYPE

sealed class Row {
    abstract val id: String

    object Header : Row() {
        override val id: String
            get() = TITLE_TYPE.toString()
    }

    open class Content(override val id: String) : Row()

}
