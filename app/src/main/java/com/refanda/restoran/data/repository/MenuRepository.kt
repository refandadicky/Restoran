package com.refanda.restoran.data.repository

import com.refanda.restoran.data.datasource.menu.MenuDataSource
import com.refanda.restoran.data.mapper.toMenu
import com.refanda.restoran.data.model.Menu
import com.refanda.restoran.utils.ResultWrapper
import com.refanda.restoran.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenu(categorySlug : String? = null): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(
    private val dataSource: MenuDataSource
):MenuRepository{
    override fun getMenu(categorySlug : String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            dataSource.getMenu(categorySlug).data.toMenu()
        }
    }
}