package humbertocosta.com.omdbchallenge.ui.movies.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import humbertocosta.com.omdbchallenge.data.network.Resource
import humbertocosta.com.omdbchallenge.data.network.response.SearchMoviesByTitleResponse
import humbertocosta.com.omdbchallenge.data.repository.MoviesRepository


class SearchMoviesListViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val searchTermLiveData = MutableLiveData<String>()
    private val movies: LiveData<Resource<SearchMoviesByTitleResponse>> = Transformations.switchMap(searchTermLiveData) { searchTerm ->
        moviesRepository.searchMoviesByTitle(searchTerm)
    }

    fun getMovies(): LiveData<Resource<SearchMoviesByTitleResponse>> = movies

    fun updateSearchTerm(searchTerm: String) {
        searchTermLiveData.value = searchTerm
    }
}