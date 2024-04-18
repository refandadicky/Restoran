package com.refanda.restoran.data.datasource.category

import com.refanda.restoran.data.source.network.model.category.CategoriesResponse
import com.refanda.restoran.data.source.network.services.RestoranApiService

class CategoryApiDataSource (
    private val service: RestoranApiService
) : CategoryDataSource {
    override suspend fun getCategories(): CategoriesResponse {
        return service.getCategories()
    }
}