package com.odai.mobilecomputing

data class QuoteX(
    val author: String,
    val background: String,
    val category: String,
    val date: String,
    val id: String,
    val language: String,
    val length: String,
    val permalink: String,
    val quote: String,
    val tags: List<String>,
    val title: String
)