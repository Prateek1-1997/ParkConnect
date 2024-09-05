package com.vehicle.owner.data.repository

import com.vehicle.owner.data.remote.datasource.ISearchRemoteDataSource
import com.vehicle.owner.domain.respository.ISearchRepository
import com.vehicle.owner.network.NetworkResultWrapper
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val iSearchRemoteDataSource: ISearchRemoteDataSource,
) : ISearchRepository {
    override suspend fun getSearchDetails(query: String): NetworkResultWrapper<Pair<String, String>> {
         return iSearchRemoteDataSource.getSearchDetails(query)
    }


}