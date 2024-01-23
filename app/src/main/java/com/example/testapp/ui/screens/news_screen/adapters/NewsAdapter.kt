package com.example.testapp.ui.screens.news_screen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.models.News
import com.example.testapp.R
import com.example.testapp.databinding.ItemNewsBinding

interface NewsAdapterDelegate {
    fun onNewsClicked(news: News)
}

class NewsRecyclerViewAdapter(private var delegate: NewsAdapterDelegate) :
    ListAdapter<News, NewsRecyclerViewAdapter.ViewHolder>(
        diffUtil
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemNewsBinding.inflate(
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
        private val binding: ItemNewsBinding,
        private var delegate: NewsAdapterDelegate
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(news: News) {
            binding.apply {
                Glide.with(root.context).load(news.image).into(image)
                title.text = news.header
                description.text = news.description
                source.text = news.source
                date.text = news.publishedAt

                root.setOnClickListener {
                    delegate.onNewsClicked(news)
                }
            }
        }
    }


    companion object {
        var diffUtil = object : DiffUtil.ItemCallback<News>() {

            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }
    }


}