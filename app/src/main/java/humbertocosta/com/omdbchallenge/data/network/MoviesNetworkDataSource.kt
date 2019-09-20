package humbertocosta.com.omdbchallenge.data.network

import androidx.lifecycle.LiveData
import humbertocosta.com.omdbchallenge.data.network.response.MovieDetailsResponse
import humbertocosta.com.omdbchallenge.data.network.response.SearchMoviesByTitleResponse

interface MoviesNetworkDataSource {
    val downloadedSearchMoviesByTitle: LiveData<Resource<SearchMoviesByTitleResponse>>
    val downloadedMovieDetailsById: LiveData<Resource<MovieDetailsResponse>>

    suspend fun fetchSearchMoviesByTitle(title: String)
    suspend fun fetchMovieDetailsById(id: String)
}