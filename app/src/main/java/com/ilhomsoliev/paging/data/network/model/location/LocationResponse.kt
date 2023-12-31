package com.ilhomsoliev.rikandmortytest.data.network.model.location

data class LocationResponse(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)