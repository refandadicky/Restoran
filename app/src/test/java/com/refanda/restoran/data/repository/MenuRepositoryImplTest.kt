package com.refanda.restoran.data.repository

import app.cash.turbine.test
import com.refanda.restoran.data.datasource.menu.MenuDataSource
import com.refanda.restoran.data.source.network.model.menu.MenuItemResponse
import com.refanda.restoran.data.source.network.model.menu.MenuResponse
import com.refanda.restoran.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MenuRepositoryImplTest {
    @MockK
    lateinit var datasource: MenuDataSource
    private lateinit var menuRepository: MenuRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        menuRepository = MenuRepositoryImpl(datasource)
    }

    @Test
    fun getMenu() {
        val menu1 =
            MenuItemResponse(
                id = "1",
                name = "fbsabj",
                img_url = "hasbjabfai",
                price = 80000.0,
                desc = "dhbfbfuewnfoiiwfmifnienrigninbkrwnvkdsnjdfjw",
                address = "enjfninfinienvknskxnifekfoefoglmvlmknifgi",
                mapsUrl = "ndnfifiejwefo.com",
            )
        val menu2 =
            MenuItemResponse(
                id = "2",
                name = "whdiwna",
                img_url = "acuoidjismcmasjos",
                price = 100000.0,
                desc = "bewuiwdijwqodomqccwevefewfevevdzvwrgwefsadas",
                address = "asjoijnfiwjifjqmcksaaokenonf",
                mapsUrl = "nefidjaskdmlmalkpoqfq.com",
            )

        val mockListMenu = listOf(menu1, menu2)
        val mockResponse = mockk<MenuResponse>()
        every { mockResponse.data } returns mockListMenu
        runTest {
            coEvery { datasource.getMenu(any()) } returns mockResponse
            menuRepository.getMenu("makanan").map {
                delay(100)
                it
            }.test {
                delay(2210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { datasource.getMenu(any()) }
            }
        }
    }

    @Test
    fun getMenu_when_category_null() {
        val menu1 =
            MenuItemResponse(
                id = "1",
                name = "fbsabj",
                img_url = "hasbjabfai",
                price = 80000.0,
                desc = "dhbfbfuewnfoiiwfmifnienrigninbkrwnvkdsnjdfjw",
                address = "enjfninfinienvknskxnifekfoefoglmvlmknifgi",
                mapsUrl = "ndnfifiejwefo.com",
            )
        val menu2 =
            MenuItemResponse(
                id = "2",
                name = "whdiwna",
                img_url = "acuoidjismcmasjos",
                price = 100000.0,
                desc = "bewuiwdijwqodomqccwevefewfevevdzvwrgwefsadas",
                address = "asjoijnfiwjifjqmcksaaokenonf",
                mapsUrl = "nefidjaskdmlmalkpoqfq.com",
            )

        val mockListMenu = listOf(menu1, menu2)
        val mockResponse = mockk<MenuResponse>()
        every { mockResponse.data } returns mockListMenu
        runTest {
            coEvery { datasource.getMenu(any()) } returns mockResponse
            menuRepository.getMenu().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { datasource.getMenu(any()) }
            }
        }
    }

    @Test
    fun createOrder() {
    }
}
