package humbertocosta.com.omdbchallenge.data.network

import androidx.lifecycle.LiveData
import humbertocosta.com.omdbchallenge.data.network.response.MovieDetailsResponse
import humbertocosta.com.omdbchallenge.data.network.response.SearchMoviesByTitleResponse

interface MoviesNetworkDataSource {
    val downloadedSearchMoviesByTitle: LiveData<SearchMoviesByTitleResponse>
    val downloadedMovieDetailsById: LiveData<MovieDetailsResponse>

    suspend fun fetchSearchMoviesByTitle(title: String)
    suspend fun fetchMovieDetailsById(id: String)
}