package com.example.twitterprivacy.models

data class Meta(
    val newest_id: String,
    val next_token: String,
    val oldest_id: String,
    val result_count: Int
)