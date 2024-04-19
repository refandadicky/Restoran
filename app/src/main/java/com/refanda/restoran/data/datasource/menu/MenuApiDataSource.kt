package com.refanda.restoran.data.datasource.menu

import com.refanda.restoran.data.source.network.model.checkout.CheckoutRequestPayload
import com.refanda.restoran.data.source.network.model.checkout.CheckoutResponse
import com.refanda.restoran.data.source.network.model.menu.MenuResponse
import com.refanda.restoran.data.source.network.services.RestoranApiService

class MenuApiDataSource (
    private val service: RestoranApiService
) : MenuDataSource {
    override suspend fun getMenu(categorySlug: String?): MenuResponse {
        return service.getMenu(categorySlug)
    }

    override suspend fun createOrder(payload: CheckoutRequestPayload): CheckoutResponse {
        return service.createOrder(payload)
    }
}