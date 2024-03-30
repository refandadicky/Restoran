package com.refanda.restoran.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.refanda.restoran.R
import com.refanda.restoran.data.datasourcedummy.DummyCategoryDataSource
import com.refanda.restoran.data.datasourcedummy.DummyMenuDataSource
import com.refanda.restoran.data.model.Category
import com.refanda.restoran.data.model.Menu
import com.refanda.restoran.data.repository.CategoryRepository
import com.refanda.restoran.data.repository.CategoryRepositoryImpl
import com.refanda.restoran.data.repository.MenuRepository
import com.refanda.restoran.data.repository.MenuRepositoryImpl
import com.refanda.restoran.data.source.local.pref.UserPreferenceImpl
import com.refanda.restoran.databinding.FragmentHomeBinding
import com.refanda.restoran.presentation.home.adapter.CategoryAdapter
import com.refanda.restoran.presentation.home.adapter.ListMenuAdapter
import com.refanda.restoran.presentation.home.adapter.OnItemClickedListener
import com.refanda.restoran.presentation.menudetail.MenuDetailActivity
import com.refanda.restoran.utils.GenericViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var menuAdapter: ListMenuAdapter? = null

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter{

        }
    }

    private var isUsingGridMode: Boolean = false

    private val viewModel: HomeViewModel by viewModels {
        val menuDataSource = DummyMenuDataSource()
        val menuRepository : MenuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource = DummyCategoryDataSource()
        val categoryRepository : CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        val userPreference = UserPreferenceImpl(requireContext())
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository, userPreference))
    }

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
        bindCategory(viewModel.getCategories())
        observeGridMode()
        setClickAction()
    }


    private fun observeGridMode() {
        viewModel.isUsingGridMode.observe(viewLifecycleOwner) { isUsingGridMode ->
            setButtonIcon(isUsingGridMode)
            bindMenuList(isUsingGridMode, viewModel.getMenu())
        }
    }

    private fun bindCategory(data: List<Category>) {
        binding.rvCategory.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        categoryAdapter.submitData(data)
    }

    private fun setClickAction() {
        binding.layoutMenu.icListmenu.setOnClickListener {
            viewModel.changeListMode()
        }
    }

    private fun setButtonIcon(usingGridMode: Boolean) {
        binding.layoutMenu.icListmenu.setImageResource(if (usingGridMode) R.drawable.ic_grid_view else R.drawable.ic_list)
    }

    private fun bindMenuList(isUsingGrid: Boolean, data: List<Menu>) {
        val listMode = if (isUsingGrid) ListMenuAdapter.MODE_GRID else ListMenuAdapter.MODE_LIST
        menuAdapter = ListMenuAdapter(
            listMode = listMode,
            listener = object : OnItemClickedListener<Menu> {
                override fun onItemClicked(item: Menu) {
                    navigateToDetail(item)
                }
            })
        binding.layoutMenu.rvListmenu.apply {
            adapter = this@HomeFragment.menuAdapter
            layoutManager = GridLayoutManager(requireContext(),if (isUsingGrid) 2 else 1)
        }
        menuAdapter?.submitData(data)
    }

    private fun navigateToDetail(item: Menu){
        MenuDetailActivity.startActivity(
            requireContext(),
            item
        )
    }
}