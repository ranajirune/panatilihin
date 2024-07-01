package com.raineru.panatilihin.data.tempo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "dogs")
data class Dog(
    @PrimaryKey(autoGenerate = true)
    val dogId: Long,

    val dogOwnerId: Long,
    val dogName: String
)

data class DogAndTheirOwners(
    @Embedded
    val dog: Dog,

    @Relation(
        parentColumn = "dogId",
        entityColumn = "ownerId",
        associateBy = Junction(OwnerDogCrossRef::class)
    )
    val owners: List<Owner>?
)