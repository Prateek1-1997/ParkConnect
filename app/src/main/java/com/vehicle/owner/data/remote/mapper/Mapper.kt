package com.vehicle.owner.data.remote.mapper

import com.vehicle.owner.data.response.ChatDto
import com.vehicle.owner.data.response.DtoItems
import com.vehicle.owner.data.response.UserDto
import com.vehicle.owner.data.response.VerifyOtpDto
import com.vehicle.owner.domain.model.ChatModel
import com.vehicle.owner.domain.model.UserModel
import com.vehicle.owner.domain.model.VerifyOtpModel
import kotlin.random.Random

object Mapper {

    fun VerifyOtpDto.toDomainModel(): VerifyOtpModel {
        return VerifyOtpModel(
            message = this.message.orEmpty(),
            isVerified = this.isVerified ?: false,
            token = this.token.orEmpty(),
        )
    }

    fun DtoItems<ChatDto>.toDomainModel(): List<ChatModel> {
        return this.items?.map {
            ChatModel(
                id = it.id ?: Random.nextLong().toString(),
                message = it.message.orEmpty(),
                sender = it.sender.orEmpty(),
                receiver = it.receiver.orEmpty(),
                timeStamp = it.timeStamp.orEmpty(),
                direction = it.direction ?: false
            )
        } ?: run { emptyList() }
    }

    fun UserDto?.toDomainModel() : UserModel {
        return UserModel(
            this?.phone.orEmpty(),
            this?.isVerified?:false,
            this?.createdDate.orEmpty(),
            this?.updatedDate.orEmpty(),
            this?.name.orEmpty(),
            this?.userId.orEmpty(),
            arrayListOf()
        )
    }
}