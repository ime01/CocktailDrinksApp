package com.flowz.drinkcocktails.utils


data class Resource <out T>(val status: Status, val data: T?, val message: String?) {

    companion object{
        fun <T> success(data: T?): Resource<T>{
            return Resource(Status.SUCCESS, data, null)
        }
        fun <T> error(msg:String, data: T?): Resource<T>{
            return Resource(Status.ERROR, data, null)
        }

        fun <T> loading(data: T?): Resource<T>{
            return Resource(Status.LOADING, data, null)
        }
    }


}


data class Resource2<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource2<T> {
            return Resource2(Status.SUCCESS, data, null)
        }

        // fun <T> error(msg: String): Resource<T> {
        fun  error(msg: String): Resource2<Nothing> {
            return Resource2(Status.ERROR, null, msg)
        }

        //fun <T> loading(): Resource<T> {
        fun  loading(): Resource2<Nothing> {
            return Resource2(Status.LOADING, null, null)
        }

    }

}





enum class Status{ SUCCESS, ERROR, LOADING}