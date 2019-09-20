package humbertocosta.com.omdbchallenge.data.repository

import androidx.lifecycle.LiveData
import humbertocosta.com.omdbchallenge.data.network.response.MovieDetailsResponse
import humbertocosta.com.omdbchallenge.data.network.response.SearchMoviesByTitleResponse

interface MoviesRepository {
    suspend fun searchMoviesByTitle(title: String): LiveData<SearchMoviesByTitleResponse>

    suspend fun getMovieDetailsById(id: String): LiveData<MovieDetailsResponse>
}