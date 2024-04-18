package com.refanda.restoran.data.source.network.model.menu

import com.google.gson.annotations.SerializedName

data class MenuItemResponse (
    @SerializedName("id")
    val id : String?,
    @SerializedName("nama")
    val name : String?,
    @SerializedName("image_url")
    val img_url : String?,
    @SerializedName("harga")
    val price : Double?,
    @SerializedName("detail")
    val desc : String?,
    @SerializedName("alamat_resto")
    val address : String?,
    @SerializedName("mapsUrl")
    val mapsUrl : String?,
)