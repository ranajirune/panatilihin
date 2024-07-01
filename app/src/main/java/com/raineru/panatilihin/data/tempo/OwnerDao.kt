package com.raineru.panatilihin.data.tempo

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface OwnerDao {

    @Transaction
    @Query("SELECT * FROM owners")
    fun getOwners(): Flow<List<OwnerAndADog>>

    @Transaction
    @Query("SELECT * FROM owners")
    fun getOwnerAndDogs(): Flow<List<OwnerAndDogs>>

    @Transaction
    @Query("SELECT * FROM owners")
    fun getOwnerAndTheirDogs(): Flow<List<OwnerAndTheirDogs>>

    @Transaction
    @Query("SELECT * FROM dogs")
    fun getDogAndTheirOwners(): Flow<List<DogAndTheirOwners>>
}

@Entity(
    tableName = "owner_dog_cross_ref",
    primaryKeys = ["ownerId", "dogId"],
)
data class OwnerDogCrossRef(
    val ownerId: Long,
    val dogId: Long
)



