package com.example.techchallenge.data.model

data class Invites(
    val pending_invitations_count: Int,
    val profiles: List<Profile>,
    val totalPages: Int
)