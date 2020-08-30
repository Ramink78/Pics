package com.unsplash.retrofit.network
enum class Status {
    LOADING,
    LOUDED,
    FAILD
}
class NetworkState(val status:Status,val message:String){
    companion object{
         val PROSSECING:NetworkState = NetworkState(Status.LOADING,"Loading...")
        val SUCCESS:NetworkState = NetworkState(Status.LOUDED,"Successful")
        val ERROR:NetworkState = NetworkState(Status.FAILD,"Error")
    }


}




