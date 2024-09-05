package com.vehicle.owner.data.remote.datasource

import com.vehicle.owner.data.remote.service.SearchService
import com.vehicle.owner.network.NetworkResultWrapper
import com.vehicle.owner.network.safeApiCall
import javax.inject.Inject

class SearchRemoteDataSourceImpl @Inject constructor(
    private val searchService: SearchService,
) : ISearchRemoteDataSource {

    override suspend fun getSearchDetails(query: String): NetworkResultWrapper<Pair<String, String>> {
        safeApiCall { searchService.getSearchDetails(query) }.onSuccess {
            return if (it.data?.vehicle_info?.isNotEmpty() == true) {
                NetworkResultWrapper.Success(Pair(it.data.vehicle_info[0].user_id,it.data.vehicle_info[0].vehicle_number))
            } else {
                NetworkResultWrapper.Success(Pair("",""))
            }
        }.onFailure {
            return NetworkResultWrapper.Error(
                throwable = it,
            )
        }
        return NetworkResultWrapper.Error(
            throwable = null,
        )
    }

}