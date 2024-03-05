package com.example.newsapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapi.databinding.ListItemNewsBinding


class NewsHolder(
        private val binding: ListItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.newsTitle.text = news.title
            binding.newsDescription.text = news.description

            binding.root.setOnClickListener {
                //onNewsClicked()
            }
        }





class NewsListAdapter(
        private val News_List: List<News>,
        //private val onNewsClicked: () -> Unit
    ) : RecyclerView.Adapter<NewsHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ) : NewsHolder {

            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemNewsBinding.inflate(inflater, parent, false)
            return NewsHolder(binding)
            }

        override fun onBindViewHolder(holder: NewsHolder, position: Int) {
            val news = News_List[position]
            holder.bind(news)//, onNewsClicked)

        }
        override fun getItemCount() = News_List.size


    }


}