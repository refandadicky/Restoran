package com.refanda.restoran.data.mapper

import com.refanda.restoran.data.model.Menu
import com.refanda.restoran.data.source.network.model.menu.MenuItemResponse

fun MenuItemResponse?.toMenu() =
    Menu(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        price = this?.price ?: 0.0,
        imgUrl = this?.img_url.orEmpty(),
        desc = this?.desc.orEmpty(),
        address = this?.address.orEmpty(),
        mapsUrl = this?.mapsUrl.orEmpty(),
    )

fun Collection<MenuItemResponse>?.toMenu() =
    this?.map {
        it.toMenu()
    } ?: listOf()
