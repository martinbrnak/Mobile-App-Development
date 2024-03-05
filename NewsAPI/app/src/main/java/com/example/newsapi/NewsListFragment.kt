package com.example.newsapi


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapi.databinding.FragmentNewsListBinding
import kotlinx.coroutines.launch

private const val TAG = "NewsListFragment"

class NewsListFragment: Fragment() {
    private var _binding: FragmentNewsListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val newsListViewModel: NewsListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total news: ${newsListViewModel.News_List.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)

        binding.newsRecyclerView.layoutManager = LinearLayoutManager(context)

        val news_List = newsListViewModel.News_List
        val adapter = NewsHolder.NewsListAdapter(news_List)
        binding.newsRecyclerView.adapter = adapter

        return binding.root
    }
/*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                newsListViewModel.News_List.collect ( News_List ->
                    binding.newsRecyclerView.adapter =
                        NewsHolder.NewsListAdapter(News_List){
                            findNavController().navigate(
                                R.id.show_news_detail
                            )
                        }
                )
            }
        }
    }*/


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}