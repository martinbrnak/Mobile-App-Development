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
import java.util.UUID

class NewsListViewModel : ViewModel() {
    val News_List = mutableListOf<News>()

    init{
        //getNews()
        for(i in 0 until 30){
            /*if (i%2 == 0){
            val news = News(
                id = UUID.randomUUID(),
                title = "Title" + i,
                content = "",
                description = "description" + i,
                url = "",
                source = ""
            )
            }
            else{
                val news = News.getNews()
            }
            News_List += news*/
            GlobalScope.launch(Dispatchers.IO){
                getNews()
            }
        }
        }

    private fun getNews(){
        GlobalScope.launch(Dispatchers.IO) {
            val API_KEY = "cc8d12884b9242639a3559e6dcd24565"
            val URL = "https://newsapi.org/v2/everything?country=us&pageSize=30&apiKey=$API_KEY"
            try {
                val url = URL(URL)
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

            // Limiting the number of searches to 10
            val searchLimit = minOf(articlesArray.length(), 10)

            for (i in 0 until searchLimit) {
                val articleObject = articlesArray.getJSONObject(i)
                val id = UUID.randomUUID()
                val title = articleObject.getString("title")
                val source = articleObject.getJSONObject("source").getString("name")
                val description = articleObject.getString("description")
                val url = articleObject.getString("url")
                val content = articleObject.getString("content")

                val news = News(id, title, source, description, url, content)
                News_List.add(news)
            }
        }

    }
}