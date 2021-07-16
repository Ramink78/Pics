package pics.app.network

enum class Status {
    INITIALIZING,
    LOADING,
    LOADED,
    FAILED
}

class NetworkState(val status: Status, val message: String) {
    companion object {
        val PROCESSING: NetworkState = NetworkState(Status.LOADING, "Loading...")
        val INITIALIZING: NetworkState = NetworkState(Status.INITIALIZING, "Initializing...")
        val SUCCESS: NetworkState = NetworkState(Status.LOADED, "Successful")
        val ERROR: NetworkState = NetworkState(Status.FAILED, "Error")
    }


}




