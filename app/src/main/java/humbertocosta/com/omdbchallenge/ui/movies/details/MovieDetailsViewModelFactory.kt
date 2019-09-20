package humbertocosta.com.omdbchallenge.ui.movies.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import humbertocosta.com.omdbchallenge.data.repository.MoviesRepository

class MovieDetailsViewModelFactory(
    private val moviesRepository: MoviesRepository,
    private val imdbID: String
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(moviesRepository, imdbID) as T
    }
}