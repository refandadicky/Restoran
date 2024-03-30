package com.refanda.restoran.data.datasource.category

import com.refanda.restoran.R
import com.refanda.restoran.data.model.Category

interface CategoryDataSource{
    fun getCategoryList() : List<Category>
}
