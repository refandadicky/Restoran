package com.refanda.restoran.data.repository

import app.cash.turbine.test
import com.refanda.restoran.data.datasource.category.CategoryDataSource
import com.refanda.restoran.data.source.network.model.category.CategoriesResponse
import com.refanda.restoran.data.source.network.model.category.CategoryItemResponse
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class CategoryRepositoryImplTest {
    @MockK
    lateinit var dataSource: CategoryDataSource
    private lateinit var repository: CategoryRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = CategoryRepositoryImpl(dataSource)
    }

    @Test
    fun getCategories_loading() {
        val c1 =
            CategoryItemResponse(
                id = "123",
                name = "psu",
                imgUrl = "awfawf",
            )
        val c2 =
            CategoryItemResponse(
                id = "12342",
                name = "psuwaf",
                imgUrl = "awfaafawfwawf",
            )
        val mockListCategory = listOf(c1, c2)
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategories() } returns mockResponse
            repository.getCategory().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Loading)
                coVerify { dataSource.getCategories() }
            }
        }
    }

    @Test
    fun getCategories_success() {
        val c1 =
            CategoryItemResponse(
                id = "123",
                name = "psu",
                imgUrl = "awfawf",
            )
        val c2 =
            CategoryItemResponse(
                id = "12342",
                name = "psuwaf",
                imgUrl = "awfaafawfwawf",
            )
        val mockListCategory = listOf(c1, c2)
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategories() } returns mockResponse
            repository.getCategory().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Success)
                coVerify { dataSource.getCategories() }
            }
        }
    }

    @Test
    fun getCategories_error() {
        runTest {
            coEvery { dataSource.getCategories() } throws IllegalStateException("Mock Error")
            repository.getCategory().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Error)
                coVerify { dataSource.getCategories() }
            }
        }
    }

    @Test
    fun getCategories_empty() {
        val mockListCategory = listOf<CategoryItemResponse>()
        val mockResponse = mockk<CategoriesResponse>()
        every { mockResponse.data } returns mockListCategory
        runTest {
            coEvery { dataSource.getCategories() } returns mockResponse
            repository.getCategory().map {
                delay(100)
                it
            }.test {
                delay(210)
                val data = expectMostRecentItem()
                assertTrue(data is ResultWrapper.Empty)
                coVerify { dataSource.getCategories() }
            }
        }
    }
}
