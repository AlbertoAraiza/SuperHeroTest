package mx.araiza.superherotest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import mx.araiza.superherotest.utils.SuperheroesSP
import mx.araiza.superherotest.model.API.Constants
import mx.araiza.superherotest.model.API.SuperheroesClient
import mx.araiza.superherotest.model.SuperheroModel
import mx.araiza.superherotest.model.database.SuperheroDAO
import mx.araiza.superherotest.model.database.SuperheroDB
import retrofit2.Callback
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivityViewModel : ViewModel() {
    var isDataSaved = MutableLiveData(false)
    var publicKey = ""
    var privateKey = ""
    var selectedHero =MutableLiveData<SuperheroModel>()

    private lateinit var callback : Callback<Any?>
    fun onLoad(shDao: SuperheroDAO, superheroesSP: SuperheroesSP){
        val fomatter = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.US)
        val ts = fomatter.format(Date())
        val hash = SuperheroesClient.generateHash(ts, privateKey, publicKey)

        callback = SuperheroesClient.createCallback { body ->
            val retrievedHeroes = ArrayList<SuperheroModel>()
            val response = Gson().toJsonTree(body).asJsonObject
            val heroList = response["data"].asJsonObject["results"].asJsonArray
            if (heroList.size() > 0) {
                heroList.forEach {
                    val hero = it.asJsonObject
                    val comics = extractArray(hero["comics"].asJsonObject)
                    val series = extractArray(hero["series"].asJsonObject)
                    val stories = extractArray(hero["stories"].asJsonObject)
                    val events = extractArray(hero["events"].asJsonObject)

                    var imgUrl: String
                    hero["thumbnail"].asJsonObject.let { thumbnail ->
                        val securePath = thumbnail["path"].asString.replace("http", "https")
                        imgUrl = "${securePath}.${thumbnail["extension"].asString}"
                        println(imgUrl)
                    }
                    val superhero = SuperheroModel(
                        name = hero["name"].asString,
                        description = hero["description"].asString,
                        imageURL = imgUrl,
                        comics = comics,
                        series = series,
                        stories = stories,
                        events = events
                    )
                    retrievedHeroes.add(superhero)
                }
                SuperheroDB.databaseWritter.execute {
                    shDao.addOrUpdateSuperhero(retrievedHeroes)
                    superheroesSP.setSaved(superheroesSP.getSaved() + retrievedHeroes.size)
                    if (!isDataSaved.value!!)isDataSaved.postValue(true)
                    SuperheroesClient.service.getCharacters(
                        publicKey,
                        hash,
                        ts,
                        superheroesSP.getSaved()
                    ).enqueue(callback)
                }
            }
        }
        SuperheroDB.databaseWritter.execute {
            val x = shDao.countSavedHeroes()
            println(x)
            if (x >= 10 ){
                if (!isDataSaved.value!!)isDataSaved.postValue(true)
            }
        }
        SuperheroesClient.service.getCharacters(publicKey, hash, ts, superheroesSP.getSaved()).enqueue(callback)

    }

    fun extractArray(items : JsonObject) :ArrayList<String>{
        val list = ArrayList<String>()
        items["items"].asJsonArray.forEach { item ->
            list.add(item.asJsonObject["name"].asString)
        }
        return list
    }
}