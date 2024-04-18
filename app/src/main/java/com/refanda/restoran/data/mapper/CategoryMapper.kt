package com.refanda.restoran.data.mapper

import com.refanda.restoran.data.model.Category
import com.refanda.restoran.data.source.network.model.category.CategoryItemResponse

fun CategoryItemResponse?.toCategory() =
    Category(
        id = this?.id.orEmpty(),
        name = this?.name.orEmpty(),
        imgUrl = this?.imgUrl.orEmpty(),
        categoryDesc = ""
    )

fun Collection<CategoryItemResponse>?.toCategories() =
    this?.map { it.toCategory() } ?: listOf()