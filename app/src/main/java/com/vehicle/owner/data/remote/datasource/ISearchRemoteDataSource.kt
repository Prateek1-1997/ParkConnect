package com.vehicle.owner.data.remote.datasource

import com.vehicle.owner.network.NetworkResultWrapper

interface ISearchRemoteDataSource {

    suspend fun getSearchDetails(query : String): NetworkResultWrapper<Pair<String, String>>




}