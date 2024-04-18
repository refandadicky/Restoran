package com.refanda.restoran.data.model

import androidx.annotation.DrawableRes
import java.util.UUID

data class Category (
    var id : String= UUID.randomUUID().toString(),
    var imgUrl: String,
    var name: String,
    var categoryDesc: String
)