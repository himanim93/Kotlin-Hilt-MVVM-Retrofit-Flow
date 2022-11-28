package com.hm.users.data.model


import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("items")
    val users: MutableList<User>
) {
    data class User(
        @SerializedName("html_url")
        val html_url: String,
        @SerializedName("login")
        val name: String,
        @SerializedName("avatar_url")
        val photoUrlSmall: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("id")
        val id: String
    )
}