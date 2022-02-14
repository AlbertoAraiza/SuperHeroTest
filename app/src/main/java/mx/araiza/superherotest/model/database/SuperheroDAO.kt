package mx.araiza.superherotest.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.araiza.superherotest.model.SuperheroModel

@Dao
interface SuperheroDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addOrUpdateSuperhero(superhero :List<SuperheroModel>)

    @Query("SELECT * FROM SuperheroModel ORDER BY RANDOM() LIMIT 10")
    fun getRandomHeroes() :List<SuperheroModel>

    @Query("SELECT COUNT(*) FROM SuperheroModel")
    fun countSavedHeroes() :LiveData<Int>
}