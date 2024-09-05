package com.vehicle.owner.domain.usecase

import com.vehicle.owner.core.CustomResult
import com.vehicle.owner.core.coroutine.CoroutineDispatcherProvider
import com.vehicle.owner.domain.respository.ISearchRepository
import com.vehicle.owner.network.NetworkResultWrapper
import com.vehicle.owner.network.executeSafeCall
import javax.inject.Inject

class GetVehicleDetailsUsecase @Inject constructor(
    private val repository: ISearchRepository,
    private val coroutineContextController: CoroutineDispatcherProvider,
) {

    suspend operator fun invoke(query:String): CustomResult<Pair<String, String>, String> =
        coroutineContextController.switchToIO {
            executeSafeCall(
                block = {
                    when (
                        val result =
                            repository.getSearchDetails(query)
                    ) {
                        is NetworkResultWrapper.Success -> {
                            CustomResult.Success(result.data)
                        }

                        is NetworkResultWrapper.Error -> {
                            CustomResult.Error(
                                result.throwable.toString(),
                            )
                        }
                    }
                },
                error = {
                    CustomResult.Error(it)
                },
            )
        }
}
