package humbertocosta.com.omdbchallenge.data.repository

import androidx.lifecycle.LiveData
import humbertocosta.com.omdbchallenge.data.network.MoviesNetworkDataSource
import humbertocosta.com.omdbchallenge.data.network.Resource
import humbertocosta.com.omdbchallenge.data.network.response.MovieDetailsResponse
import humbertocosta.com.omdbchallenge.data.network.response.SearchMoviesByTitleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(private val moviesNetworkDataSource: MoviesNetworkDataSource) : MoviesRepository {

    override suspend fun searchMoviesByTitle(title: String): LiveData<Resource<SearchMoviesByTitleResponse>> {

        return withContext(Dispatchers.IO) {
            moviesNetworkDataSource.fetchSearchMoviesByTitle(title)
            return@withContext moviesNetworkDataSource.downloadedSearchMoviesByTitle
        }
    }

    override suspend fun getMovieDetailsById(id: String): LiveData<Resource<MovieDetailsResponse>> {
        return withContext(Dispatchers.IO) {
            moviesNetworkDataSource.fetchMovieDetailsById(id)
            return@withContext moviesNetworkDataSource.downloadedMovieDetailsById
        }
    }
}