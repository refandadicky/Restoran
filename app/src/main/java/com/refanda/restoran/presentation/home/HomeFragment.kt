package com.refanda.restoran.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.refanda.restoran.R
import com.refanda.restoran.data.datasource.category.CategoryApiDataSource
import com.refanda.restoran.data.datasource.category.CategoryDataSource
import com.refanda.restoran.data.datasource.menu.MenuApiDataSource
import com.refanda.restoran.data.datasource.menu.MenuDataSource
import com.refanda.restoran.data.model.Category
import com.refanda.restoran.data.model.Menu
import com.refanda.restoran.data.repository.CategoryRepository
import com.refanda.restoran.data.repository.CategoryRepositoryImpl
import com.refanda.restoran.data.repository.MenuRepository
import com.refanda.restoran.data.repository.MenuRepositoryImpl
import com.refanda.restoran.data.source.local.pref.UserPreferenceImpl
import com.refanda.restoran.data.source.network.services.RestoranApiService
import com.refanda.restoran.databinding.FragmentHomeBinding
import com.refanda.restoran.presentation.home.adapter.CategoryAdapter
import com.refanda.restoran.presentation.home.adapter.ListMenuAdapter
import com.refanda.restoran.presentation.home.adapter.OnItemClickedListener
import com.refanda.restoran.presentation.menudetail.MenuDetailActivity
import com.refanda.restoran.utils.GenericViewModelFactory
import com.refanda.restoran.utils.proceedWhen

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels {
        val service = RestoranApiService.invoke()
        val menuDataSource : MenuDataSource = MenuApiDataSource(service)
        val menuRepository : MenuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource : CategoryDataSource = CategoryApiDataSource(service)
        val categoryRepository : CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        val userPreference = UserPreferenceImpl(requireContext())
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository, userPreference))
    }

    private val menuAdapter: ListMenuAdapter by lazy {
        ListMenuAdapter(viewModel.getListMode()){
            navigateToDetail(it)
        }
    }

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter{
            getMenuData(it.name)
        }
    }

    private var isUsingGridMode: Boolean = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListMenu()
        setupListCategory()
        observeGridMode()
        setClickAction()
        getCategoryData()
        getMenuData(null)
    }

    private fun setupListCategory() {
        binding.rvCategory.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupListMenu() {
        binding.layoutMenu.rvListmenu.apply {
            adapter = menuAdapter
        }
    }

    private fun observeGridMode() {
        viewModel.isUsingGridMode.observe(viewLifecycleOwner) { isUsingGridMode ->
            setButtonIcon(isUsingGridMode)
            changeLayout(isUsingGridMode)
        }
    }

    private fun changeLayout(usingGridMode: Boolean) {
        val listMode = if (usingGridMode) ListMenuAdapter.MODE_GRID else ListMenuAdapter.MODE_LIST
        menuAdapter.updateListMode(listMode)
        setupListMenu()
        binding.layoutMenu.rvListmenu.apply {
            layoutManager = GridLayoutManager(requireContext(),if (usingGridMode) 2 else 1)
        }
    }

    private fun setClickAction() {
        binding.layoutMenu.icListmenu.setOnClickListener {
            viewModel.changeListMode()
        }
    }

    private fun setButtonIcon(usingGridMode: Boolean) {
        binding.layoutMenu.icListmenu.setImageResource(if (usingGridMode) R.drawable.ic_grid_view else R.drawable.ic_list)
    }

    private fun getCategoryData() {
        viewModel.getCategories().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindCategory(data) }
                }
            )
        }
    }

    private fun getMenuData(categorySlug: String? = null) {
        viewModel.getMenu(categorySlug).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindMenuList(isUsingGridMode, data) }
                }
            )
        }
    }

    private fun bindCategory(data: List<Category>) {
        categoryAdapter.submitData(data)
    }

    private fun bindMenuList(isUsingGrid: Boolean, data: List<Menu>) {
        menuAdapter?.submitData(data)
    }

    private fun navigateToDetail(item: Menu){
        MenuDetailActivity.startActivity(
            requireContext(),
            item
        )
    }
}
