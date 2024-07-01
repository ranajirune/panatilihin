package com.raineru.panatilihin.data.tempo

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineOwnerRepository @Inject constructor(
    private val ownerDao: OwnerDao
) : OwnerRepository {
    override fun getOwners(): Flow<List<OwnerAndADog>> = ownerDao.getOwners()

    override fun getOwnerAndDogs(): Flow<List<OwnerAndDogs>> = ownerDao.getOwnerAndDogs()

    override fun getOwnerAndTheirDogs(): Flow<List<OwnerAndTheirDogs>> = ownerDao.getOwnerAndTheirDogs()

    override fun getDogAndTheirOwners(): Flow<List<DogAndTheirOwners>> = ownerDao.getDogAndTheirOwners()
}