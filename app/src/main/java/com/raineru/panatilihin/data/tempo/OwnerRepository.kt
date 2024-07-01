package com.raineru.panatilihin.data.tempo

import kotlinx.coroutines.flow.Flow

interface OwnerRepository {
    fun getOwners(): Flow<List<OwnerAndADog>>
    fun getOwnerAndDogs(): Flow<List<OwnerAndDogs>>
    fun getOwnerAndTheirDogs(): Flow<List<OwnerAndTheirDogs>>
    fun getDogAndTheirOwners(): Flow<List<DogAndTheirOwners>>
}