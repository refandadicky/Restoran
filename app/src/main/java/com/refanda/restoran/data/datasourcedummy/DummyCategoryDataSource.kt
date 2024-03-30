package com.refanda.restoran.data.datasourcedummy

import com.refanda.restoran.data.datasource.category.CategoryDataSource
import com.refanda.restoran.data.model.Category

class DummyCategoryDataSource : CategoryDataSource {
    override fun getCategoryList(): List<Category> {
        return mutableListOf(
            Category(imgUrl = "https://github.com/refandadicky/kokomputer-assets/blob/main/category_img/ic_category_rice.webp?raw=true", name = "Nasi"),
            Category(imgUrl = "https://github.com/refandadicky/kokomputer-assets/blob/main/category_img/ic_category_mie.jpg?raw=true", name = "Mie"),
            Category(imgUrl = "https://github.com/refandadicky/kokomputer-assets/blob/main/category_img/ic_category_snack.jpg?raw=true", name = "Snack"),
            Category(imgUrl = "https://github.com/refandadicky/kokomputer-assets/blob/main/category_img/ic_category_drink.jpg?raw=true", name = "Minuman"),
            Category(imgUrl = "https://github.com/refandadicky/kokomputer-assets/blob/main/category_img/ic_category_meatball.jpeg?raw=true", name = "Bakso"),
            Category(imgUrl = "https://github.com/refandadicky/kokomputer-assets/blob/main/category_img/ic_category_satay.jpg?raw=true", name = "Sate"),
            Category(imgUrl = "https://github.com/refandadicky/kokomputer-assets/blob/main/category_img/ic_category_chicken.webp?raw=true", name = "Ayam"),
        )
    }
}