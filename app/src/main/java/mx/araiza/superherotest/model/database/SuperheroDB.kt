package mx.araiza.superherotest.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mx.araiza.superherotest.model.SuperheroModel
import java.util.concurrent.Executors

@Database(entities = [SuperheroModel::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class SuperheroDB :RoomDatabase() {
    abstract fun getSuperheroDAO() :SuperheroDAO

    companion object{
        @Volatile private var INSTANCE :SuperheroDB? = null
        var databaseWritter = Executors.newFixedThreadPool(4)

        fun getDatabase(ctx : Context) :SuperheroDB{
            INSTANCE?: synchronized(this){
                INSTANCE = Room.databaseBuilder(ctx, SuperheroDB::class.java, "superhero_database")
                    .fallbackToDestructiveMigration().build()
            }
            return INSTANCE!!
        }
    }
}