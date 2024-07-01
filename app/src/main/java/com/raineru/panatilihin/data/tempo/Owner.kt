package com.raineru.panatilihin.data.tempo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "owners")
data class Owner(
    @PrimaryKey(autoGenerate = true)
    val ownerId: Long,

    val ownerName: String,
)

data class OwnerAndTheirDogs(
    @Embedded
    val owner: Owner,

    @Relation(
        parentColumn = "ownerId",
        entityColumn = "dogId",
        associateBy = Junction(OwnerDogCrossRef::class)
    )
    val dogs: List<Dog>?
)

data class OwnerAndADog(
    @Embedded
    val owner: Owner,

    @Relation(
        parentColumn = "ownerId",
        entityColumn = "dogOwnerId"
    )
    val dog: Dog?
)

data class OwnerAndDogs(
    @Embedded
    val owner: Owner,

    @Relation(
        parentColumn = "ownerId",
        entityColumn = "dogOwnerId"
    )
    val dogs: List<Dog>?
)