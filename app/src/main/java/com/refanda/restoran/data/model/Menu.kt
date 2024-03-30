package com.refanda.restoran.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Menu (
    var id : String?= UUID.randomUUID().toString(),
    var imgUrl: String,
    var price: Double,
    var name: String,
    var detailMenu: String,
    var address: String,
    var mapsUrl: String
) : Parcelable