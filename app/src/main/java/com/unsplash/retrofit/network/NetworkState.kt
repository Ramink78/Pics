package com.unsplash.retrofit.network

enum class Status {
    INITIALIZING,
    LOADING,
    LOUDED,
    FAILD
}

class NetworkState(val status: Status, val message: String) {
    companion object {
        val PROSSECING: NetworkState = NetworkState(Status.LOADING, "Loading...")
        val INITIALIZING: NetworkState = NetworkState(Status.INITIALIZING, "Initializing...")
        val SUCCESS: NetworkState = NetworkState(Status.LOUDED, "Successful")
        val ERROR: NetworkState = NetworkState(Status.FAILD, "Error")
    }


}




