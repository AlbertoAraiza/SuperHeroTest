package mx.araiza.superherotest.model.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun arrayListFromString(value :String) :ArrayList<String>{
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
//        val array = ArrayList<String>()
//        Gson().toJsonTree(value).asJsonArray.forEach {
//            array.add(it.asString)
//        }
//        return array
    }

    @TypeConverter
    fun stringToArrayList(value :ArrayList<String>) :String{
        return Gson().toJson(value)
    }
}