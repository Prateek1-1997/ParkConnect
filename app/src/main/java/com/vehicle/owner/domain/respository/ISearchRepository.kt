package com.vehicle.owner.domain.respository

import com.vehicle.owner.network.NetworkResultWrapper

interface ISearchRepository {

    suspend fun getSearchDetails(query : String): NetworkResultWrapper<Pair<String, String>>

}