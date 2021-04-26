package com.example.techchallenge.data.model

data class Likes(
    val can_see_profile: Boolean,
    val likes_received_count: Int,
    val profiles: List<ProfileX>
)