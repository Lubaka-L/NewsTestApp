package com.example.testapp.ui.screens.news_screen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.NewsSection
import com.example.testapp.databinding.CategoryItemBinding

class NewsSectionsAdapter(private var delegate: NewsAdapterDelegate) :
    ListAdapter<NewsSection, NewsSectionsAdapter.ViewHolder>(
        diffUtil
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), delegate
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ViewHolder(
        private var binding: CategoryItemBinding,
        private val delegate: NewsAdapterDelegate
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(newsSection: NewsSection) {
            val newsAdapter = NewsRecyclerViewAdapter(delegate)
            binding.apply {
                sectionTitle.text = newsSection.section.russialName
                sectionsList.adapter = newsAdapter
                newsAdapter.submitList(newsSection.news)
            }
        }
    }


    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<NewsSection>() {
            override fun areItemsTheSame(oldItem: NewsSection, newItem: NewsSection): Boolean {
                return oldItem.section == newItem.section
            }

            override fun areContentsTheSame(oldItem: NewsSection, newItem: NewsSection): Boolean {
                return oldItem == newItem
            }
        }
    }


}