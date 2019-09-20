package humbertocosta.com.omdbchallenge.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import humbertocosta.com.omdbchallenge.data.network.response.MovieDetailsResponse
import humbertocosta.com.omdbchallenge.data.network.response.SearchMoviesByTitleResponse
import humbertocosta.com.omdbchallenge.internal.NoConnectivityException

class MoviesNetworkDataSourceImpl(private val omDbApiService: OMDbApiService) : MoviesNetworkDataSource {

    private val _downloadedSearchMoviesByTitle = MutableLiveData<Resource<SearchMoviesByTitleResponse>>()
    override val downloadedSearchMoviesByTitle: LiveData<Resource<SearchMoviesByTitleResponse>>
        get() = _downloadedSearchMoviesByTitle


    override suspend fun fetchSearchMoviesByTitle(title: String) {
        try {
            val fetchedSearchMoviesByTitle = omDbApiService
                .searchMoviesByTitle(title)
                .await()
            _downloadedSearchMoviesByTitle.postValue(Resource.success(fetchedSearchMoviesByTitle))
        } catch (nce: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", nce)
            _downloadedSearchMoviesByTitle.postValue(Resource.error("No internet connection.", null))
        } catch (e: Exception) {
            Log.e("Exception", "Couldn't fetch.", e)
            _downloadedSearchMoviesByTitle.postValue(Resource.error(e.localizedMessage, null))
        }
    }

    private val _downloadedMovieDetails = MutableLiveData<Resource<MovieDetailsResponse>>()
    override val downloadedMovieDetailsById: LiveData<Resource<MovieDetailsResponse>>
        get() = _downloadedMovieDetails

    override suspend fun fetchMovieDetailsById(id: String) {
        try {
            val fetchedMovieDetails = omDbApiService
                .getMovieDetailsById(id)
                .await()
            _downloadedMovieDetails.postValue(Resource.success(fetchedMovieDetails))
        } catch (nce: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", nce)
        } catch (e: Exception) {
            Log.e("Exception", "Couldn't fetch.", e)
        }
    }
}