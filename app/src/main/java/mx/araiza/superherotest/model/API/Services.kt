package mx.araiza.superherotest.model.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {
    @GET("/v1/public/characters")
    fun getCharacters(@Query("apikey") apikey :String,
                     @Query("hash") hash :String,
                     @Query("ts") timestamp:String,
                     @Query("offset") offset :Int) : Call<Any>
}