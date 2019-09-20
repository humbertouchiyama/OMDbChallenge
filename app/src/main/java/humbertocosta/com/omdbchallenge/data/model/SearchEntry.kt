package humbertocosta.com.omdbchallenge.data.model


import com.google.gson.annotations.SerializedName

data class SearchEntry(
    val imdbID: String,
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Year")
    val year: String
)