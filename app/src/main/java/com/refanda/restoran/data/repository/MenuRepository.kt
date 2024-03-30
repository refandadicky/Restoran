package com.refanda.restoran.data.repository

import com.refanda.restoran.data.datasource.menu.MenuDataSource
import com.refanda.restoran.data.model.Menu

interface MenuRepository {
    fun getMenu(): List<Menu>
}

class MenuRepositoryImpl(private val dataSource: MenuDataSource):MenuRepository{
    override fun getMenu(): List<Menu> = dataSource.getMenuList()
}