package humbertocosta.com.omdbchallenge.data.repository

import androidx.lifecycle.LiveData
import humbertocosta.com.omdbchallenge.data.network.Resource
import humbertocosta.com.omdbchallenge.data.network.response.MovieDetailsResponse
import humbertocosta.com.omdbchallenge.data.network.response.SearchMoviesByTitleResponse

interface MoviesRepository {
    fun searchMoviesByTitle(title: String): LiveData<Resource<SearchMoviesByTitleResponse>>

    suspend fun getMovieDetailsById(id: String): LiveData<Resource<MovieDetailsResponse>>
}