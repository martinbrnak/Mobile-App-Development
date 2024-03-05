package com.example.newsapi

import java.util.UUID
data class News(
    val id: UUID,
    val title: String,
    val source: String,
    val description: String,
    val url: String,
    val content: String
)

