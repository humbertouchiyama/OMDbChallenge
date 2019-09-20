package humbertocosta.com.omdbchallenge.data.network.response


import com.google.gson.annotations.SerializedName
import humbertocosta.com.omdbchallenge.data.model.SearchEntry

data class SearchMoviesByTitleResponse(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val searchEntries: List<SearchEntry>,
    val totalResults: String
)