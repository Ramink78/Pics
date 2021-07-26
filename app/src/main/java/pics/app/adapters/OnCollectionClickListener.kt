package pics.app.adapters

import pics.app.data.collections.model.Collection

interface OnCollectionClickListener {
    fun onClick(id: String, collection: Collection)

}