package pl.imobitech.dogs.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDao {
    @Insert
    suspend fun insertAll(vararg dogs: DogBreed): List<Long>

    @Query("SELECT * FORM dogbread")
    suspend fun getAllDogs(): List<DogBreed>

    @Query("SELECT * FROM dogread WHERE uuid = :dogID")
    suspend fun getDog(dogId : Int): DogBreed

    @Query("DELETE FROM dogbread")
    suspend fun deleteAllDogs()

}