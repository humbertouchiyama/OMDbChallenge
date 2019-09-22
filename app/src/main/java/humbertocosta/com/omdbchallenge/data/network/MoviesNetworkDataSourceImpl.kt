package humbertocosta.com.omdbchallenge.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import humbertocosta.com.omdbchallenge.data.network.response.MovieDetailsResponse
import humbertocosta.com.omdbchallenge.data.network.response.SearchMoviesByTitleResponse
import humbertocosta.com.omdbchallenge.internal.NoConnectivityException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesNetworkDataSourceImpl(private val omDbApiService: OMDbApiService) : MoviesNetworkDataSource {

    private val _downloadedSearchMoviesByTitle = MutableLiveData<Resource<SearchMoviesByTitleResponse>>()
    override val downloadedSearchMoviesByTitle: LiveData<Resource<SearchMoviesByTitleResponse>>
        get() = _downloadedSearchMoviesByTitle


    override fun fetchSearchMoviesByTitle(title: String) {
        omDbApiService.searchMoviesByTitle(title).enqueue(object : Callback<SearchMoviesByTitleResponse> {
            override fun onFailure(call: Call<SearchMoviesByTitleResponse>, t: Throwable) {
                _downloadedSearchMoviesByTitle.postValue(Resource.error(t.localizedMessage, null))
            }

            override fun onResponse(call: Call<SearchMoviesByTitleResponse>, response: Response<SearchMoviesByTitleResponse>) {
                if (response.isSuccessful) {
                    _downloadedSearchMoviesByTitle.postValue(Resource.success(response.body()))
                } else {
                    _downloadedSearchMoviesByTitle.postValue(Resource.error("Unsuccessful", null))
                }
            }
        })
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