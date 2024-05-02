package com.refanda.restoran.data.repository

import com.refanda.restoran.data.datasource.category.CategoryDataSource
import com.refanda.restoran.data.mapper.toCategories
import com.refanda.restoran.data.model.Category
import com.refanda.restoran.utils.ResultWrapper
import com.refanda.restoran.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategory(): Flow<ResultWrapper<List<Category>>>
}

class CategoryRepositoryImpl(
    private val dataSource: CategoryDataSource,
) : CategoryRepository {
    override fun getCategory(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow { dataSource.getCategories().data.toCategories() }
    }
}
