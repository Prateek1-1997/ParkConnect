package com.vehicle.owner.domain.usecase

import com.vehicle.owner.core.CustomResult
import com.vehicle.owner.core.coroutine.CoroutineDispatcherProvider
import com.vehicle.owner.domain.respository.IOtpRepository
import com.vehicle.owner.network.NetworkResultWrapper
import com.vehicle.owner.network.executeSafeCall
import javax.inject.Inject

class UpdateUserDetailsUsecase @Inject constructor(
    private val repository: IOtpRepository,
    private val coroutineContextController: CoroutineDispatcherProvider,
) {

    suspend operator fun invoke(
        userId: String,
        name: String,
        vehicleNumber: String,
        phoneNumber: String,
    ): CustomResult<Any, String> =
        coroutineContextController.switchToIO {
            executeSafeCall(
                block = {
                    when (
                        val result =
                            repository.updateUserDetails(userId, name, vehicleNumber,phoneNumber)
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
