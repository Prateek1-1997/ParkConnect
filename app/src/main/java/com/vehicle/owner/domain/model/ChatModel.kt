package com.vehicle.owner.domain.model


data class ChatModel(
    val id: String,
    val message: String,
    val sender: String,
    val receiver: String,
    val timeStamp: String,
    val direction: Boolean,
)
