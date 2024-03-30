package com.refanda.restoran.data.datasourcedummy

import com.refanda.restoran.data.datasource.menu.MenuDataSource
import com.refanda.restoran.data.model.Menu

class DummyMenuDataSource : MenuDataSource {
    override fun getMenuList(): List<Menu> {
        return mutableListOf(
            Menu(imgUrl = "https://github.com/refandadicky/kokomputer-assets/blob/main/listmenu_img/img_menu_grilled_chicken.jpg?raw=true", name = "Ayam Bakar", price = 50000.0,
                detailMenu = "Ayam Bakar adalah hidangan kuliner Indonesia yang populer dan lezat. Ayam Bakar biasanya terbuat dari potongan ayam yang dimarinasi dengan bumbu khas Indonesia, seperti bawang putih, bawang merah, jahe, kunyit, dan berbagai rempah-rempah lainnya. Setelah dimarinasi, potongan ayam tersebut kemudian dipanggang atau dibakar hingga matang dan berwarna kecoklatan.",
                address = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(imgUrl = "https://github.com/refandadicky/kokomputer-assets/blob/main/listmenu_img/img_menu_fried_chicken.webp?raw=true", name = "Ayam Goreng", price = 40000.0,
                detailMenu = "Ayam Goreng adalah hidangan khas Indonesia yang terdiri dari potongan ayam yang digoreng hingga kecokelatan dan renyah. Proses memasaknya melibatkan penyemprotan atau pencelupan ayam dalam bumbu rempah-rempah khas, yang dapat bervariasi tergantung pada daerah atau preferensi pribadi. Hidangan ini sering disajikan sebagai menu utama dalam berbagai kesempatan, mulai dari makan sehari-hari hingga acara-acara khusus.",
                address = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(imgUrl = "https://github.com/refandadicky/kokomputer-assets/blob/main/listmenu_img/img_menu_chicken_smashed.jpg?raw=true", name = "Ayam Geprek", price = 40000.0,
                detailMenu = "Ayam Geprek adalah hidangan kuliner yang berasal dari Indonesia, yang terkenal dengan rasa pedas dan cita rasa yang khas. Hidangan ini umumnya terdiri dari potongan daging ayam yang digeprek atau dipukul hingga pipih, kemudian digoreng dengan tepung atau tanpa tepung hingga cokelat keemasan dan renyah. Setelah digoreng, ayam tersebut kemudian disajikan dengan sambal pedas yang dapat disesuaikan tingkat kepedasannya sesuai selera, serta kadang-kadang disertai dengan taburan bawang goreng dan irisan tomat.",
                address = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(imgUrl = "https://github.com/refandadicky/kokomputer-assets/blob/main/listmenu_img/img_menu_chicken_intestine_satay.jpeg?raw=true", name = "Sate Usus Ayam", price = 5000.0,
                detailMenu = "Sate Usus Ayam adalah hidangan tradisional Indonesia yang terdiri dari usus ayam yang ditusuk menggunakan tusuk sate, kemudian dipanggang atau dibakar hingga matang. Hidangan ini biasanya disajikan dengan bumbu kacang sebagai saus utamanya.",
                address = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(imgUrl = "https://github.com/refandadicky/kokomputer-assets/blob/main/listmenu_img/img_menu_french_fries.webp?raw=true", name = "Kentang Goreng", price = 15000.0,
                detailMenu = "Kentang goreng adalah hidangan yang terbuat dari potongan kentang yang digoreng hingga kecokelatan dan renyah di luar, namun lembut di dalam. Proses pembuatan kentang goreng umumnya melibatkan memotong kentang menjadi bentuk batangan atau irisan, kemudian menggorengnya dalam minyak panas hingga matang dan berwarna keemasan.",
                address = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"),
            Menu(imgUrl = "https://github.com/refandadicky/kokomputer-assets/blob/main/listmenu_img/img_menu_ice_coffee.jpg?raw=true", name = "Ice Coffee", price = 35000.0,
                detailMenu = "Es kopi adalah minuman yang terbuat dari kopi yang disajikan dingin atau dengan tambahan es batu. Proses pembuatannya melibatkan penyeduhan kopi yang kemudian didinginkan, baik dengan cara dituangkan langsung ke dalam gelas yang berisi es batu atau dengan mencampurkan kopi dengan es batu. Es kopi dapat disajikan dalam berbagai varian, mulai dari yang sederhana seperti es kopi hitam hingga yang lebih kompleks seperti es kopi susu atau es kopi dengan tambahan sirup dan whipped cream.",
                address = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                mapsUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77")
        )
    }
}