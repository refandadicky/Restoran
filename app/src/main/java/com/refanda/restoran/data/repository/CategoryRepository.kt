package com.refanda.restoran.data.repository

import com.refanda.restoran.data.datasource.category.CategoryDataSource
import com.refanda.restoran.data.model.Category

interface CategoryRepository {
    fun getCategory(): List<Category>
}

class CategoryRepositoryImpl(private val dataSource: CategoryDataSource):CategoryRepository{
    override fun getCategory(): List<Category> = dataSource.getCategoryList()
}