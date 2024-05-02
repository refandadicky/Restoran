package com.refanda.restoran.data.repository

import com.refanda.restoran.data.datasource.menu.MenuDataSource
import com.refanda.restoran.data.mapper.toMenu
import com.refanda.restoran.data.model.Cart
import com.refanda.restoran.data.model.Menu
import com.refanda.restoran.data.source.network.model.checkout.CheckoutItemPayload
import com.refanda.restoran.data.source.network.model.checkout.CheckoutRequestPayload
import com.refanda.restoran.utils.ResultWrapper
import com.refanda.restoran.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getMenu(categorySlug: String? = null): Flow<ResultWrapper<List<Menu>>>

    fun createOrder(
        profile: String,
        menus: List<Cart>,
        totalPrice: Int,
    ): Flow<ResultWrapper<Boolean>>
}

class MenuRepositoryImpl(
    private val dataSource: MenuDataSource,
) : MenuRepository {
    override fun getMenu(categorySlug: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            dataSource.getMenu(categorySlug).data.toMenu()
        }
    }

    override fun createOrder(
        profile: String,
        menus: List<Cart>,
        totalPrice: Int,
    ): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.createOrder(
                CheckoutRequestPayload(
                    total = totalPrice,
                    username = profile,
                    orders =
                        menus.map {
                            CheckoutItemPayload(
                                nama = it.menuName,
                                qty = it.itemQuantity,
                                catatan = it.itemNotes.orEmpty(),
                                harga = it.menuPrice.toInt(),
                            )
                        },
                ),
            ).status ?: false
        }
    }
}
