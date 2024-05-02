package com.refanda.restoran.data.datasource.menu

import com.refanda.restoran.data.source.network.model.checkout.CheckoutRequestPayload
import com.refanda.restoran.data.source.network.model.checkout.CheckoutResponse
import com.refanda.restoran.data.source.network.model.menu.MenuResponse

interface MenuDataSource {
    suspend fun getMenu(categorySlug: String? = null): MenuResponse

    suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse
}
