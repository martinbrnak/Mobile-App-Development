package com.example.newsapi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class NewsListViewModel : ViewModel() {
    val News_List = mutableListOf<News>()

    init{
        getNews()
        }

    private fun getNews(){
        GlobalScope.launch(Dispatchers.IO) {
            val API_KEY = "cc8d12884b9242639a3559e6dcd24565"
            val URL = "https://newsapi.org/v2/top-headlines?country=us&apiKey=$API_KEY&pageSize=30"
            try {
                val url = URL(API_KEY)
                val connection = url.openConnection() as HttpURLConnection
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }
                    reader.close()
                    parseNewsResponse(response.toString())
                } else {
                    // Handle error response
                    println("Error: $responseCode")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

        /// Fetch News and ParseNews created by ChatGPT

    private suspend fun parseNewsResponse(response: String) {
        withContext(Dispatchers.Default) {
            val jsonObject = JSONObject(response)
            val articlesArray = jsonObject.getJSONArray("articles")
            for (i in 0 until articlesArray.length()) {
                val articleObject = articlesArray.getJSONObject(i)
                val title = articleObject.getString("title")
                val source = articleObject.getJSONObject("source").getString("name")
                val description = articleObject.getString("description")
                val url = articleObject.getString("url")
                val content = articleObject.getString("content")

                val news = News(title, source, description, url, content)
                News_List += news
            }
        }

    }
}