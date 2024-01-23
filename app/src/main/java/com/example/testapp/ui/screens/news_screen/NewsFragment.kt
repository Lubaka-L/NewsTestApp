package com.example.testapp.ui.screens.news_screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.core.ResultWrapperUI
import com.example.core.Toast.showToast
import com.example.domain.models.News
import com.example.testapp.R
import com.example.testapp.databinding.FragmentNewsBinding
import com.example.testapp.ui.screens.news_screen.adapters.NewsAdapterDelegate
import com.example.testapp.ui.screens.news_screen.adapters.NewsSectionsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : Fragment(), NewsAdapterDelegate {

    private lateinit var binding: FragmentNewsBinding
    private val viewModel: NewsViewModel by viewModel()

    private var newsSectionsAdapter: NewsSectionsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newsSectionsAdapter = NewsSectionsAdapter(this)
        binding.sectionsList.adapter = newsSectionsAdapter

        lifecycleScope.launch {
            viewModel.news.collectLatest { result ->
                when (result) {
                    is ResultWrapperUI.Success -> {
                        binding.loading.root.isVisible(false)
                        newsSectionsAdapter?.submitList(result.data)
                        binding.noInternetConnectionAlert.visibility = View.GONE
                        if (result.data.isEmpty()){
                            showToast(R.string.error)
                        }
                    }

                    ResultWrapperUI.Loading -> {
                        binding.loading.root.isVisible(true)
                        binding.noInternetConnectionAlert.visibility = View.GONE
                    }

                    is ResultWrapperUI.Error -> {
                        binding.loading.root.isVisible(true)
                        binding.noInternetConnectionAlert.visibility = View.VISIBLE
                        showToast(R.string.error)
                    }
                }
            }
        }

        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchNewsByTitleOrDescription(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchNewsByTitleOrDescription(newText!!)
                return true
            }
        })
    }

    override fun onNewsClicked(news: News) {
        startActivity(Intent(Intent.ACTION_VIEW, news.url))
    }

    private fun View.isVisible(isVisible: Boolean) {
        when (isVisible) {
            true -> visibility = View.VISIBLE
            false -> visibility = View.GONE
        }
    }
}