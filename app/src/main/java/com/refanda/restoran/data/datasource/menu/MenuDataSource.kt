package com.refanda.restoran.data.datasource.menu

import com.refanda.restoran.data.model.Menu

interface MenuDataSource{
    fun getMenuList() : List<Menu>
}