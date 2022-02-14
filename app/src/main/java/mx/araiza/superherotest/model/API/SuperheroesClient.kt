package mx.araiza.superherotest.model.API

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

class SuperheroesClient {
    companion object{
        private var instance :Retrofit? = null
        private val md = MessageDigest.getInstance("MD5")

        private val okHttpClient = OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES).build()

        private fun getService() :Retrofit{
            if (instance == null){
                instance = Retrofit.Builder().baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient).build()
            }
            return instance!!
        }

        val service :Services = getService().create(Services::class.java)

        fun createCallback(onSuccess :(Any) -> Unit) :Callback<Any?>{
            return object :Callback<Any?>{
                override fun onResponse(call: Call<Any?>, response: Response<Any?>) {
                    if (response.isSuccessful && response.body() != null){
                        onSuccess(response.body()!!)
                    }else{
                        response.errorBody()?.let { Log.e(Constants.TAG, it.string()) }
                    }
                }

                override fun onFailure(call: Call<Any?>, t: Throwable) {
                    t.message?.let { Log.e(Constants.TAG, it) }
                }
            }
        }

        fun generateHash(ts :String, privateKey :String, publicKey :String) :String{
            val prehash = ts + privateKey + publicKey
            return BigInteger(1, md.digest(prehash.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}