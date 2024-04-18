package com.refanda.restoran.data.datasource.category

import com.refanda.restoran.data.source.network.model.category.CategoriesResponse

interface CategoryDataSource {
    suspend fun getCategories(): CategoriesResponse
}
