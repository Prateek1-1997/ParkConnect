package com.vehicle.owner.domain.usecase

import com.vehicle.owner.core.CustomResult
import com.vehicle.owner.core.coroutine.CoroutineDispatcherProvider
import com.vehicle.owner.domain.model.VerifyOtpModel
import com.vehicle.owner.domain.respository.IOtpRepository
import com.vehicle.owner.network.NetworkResultWrapper
import com.vehicle.owner.network.executeSafeCall
import javax.inject.Inject

class VerifyOtpUsecase @Inject constructor(
    private val repository: IOtpRepository,
    private val coroutineContextController: CoroutineDispatcherProvider,
) {

    suspend operator fun invoke(
        userId : String,
        fcmToken : String,
        firebaseUuid: String,
    ): CustomResult<VerifyOtpModel, String> =
        coroutineContextController.switchToIO {
            executeSafeCall(
                block = {
                    when (
                        val result =
                            repository.verifyOtp(userId, fcmToken, firebaseUuid)
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
