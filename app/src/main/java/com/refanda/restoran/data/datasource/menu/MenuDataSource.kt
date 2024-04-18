package com.refanda.restoran.data.datasource.menu

import com.refanda.restoran.data.source.network.model.menu.MenuResponse

interface MenuDataSource{
    suspend fun getMenu(categorySlug: String? = null): MenuResponse
}