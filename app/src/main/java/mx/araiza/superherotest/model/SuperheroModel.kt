package mx.araiza.superherotest.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class SuperheroModel(val name :String,
                          val description :String,
                          val imageURL :String,
                          val comics :ArrayList<String>,
                          val series :ArrayList<String>,
                          val stories :ArrayList<String>,
                          val events :ArrayList<String>) :Serializable{
                              @PrimaryKey(autoGenerate = true) var id = 0
                          }