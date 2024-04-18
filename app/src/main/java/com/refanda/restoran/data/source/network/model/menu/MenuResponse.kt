package com.refanda.restoran.data.source.network.model.menu

import com.google.gson.annotations.SerializedName

data class MenuResponse(
    @SerializedName("status")
    val status: String?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: List<MenuItemResponse>?,
)