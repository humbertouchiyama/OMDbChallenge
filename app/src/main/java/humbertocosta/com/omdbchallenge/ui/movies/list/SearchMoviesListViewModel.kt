package humbertocosta.com.omdbchallenge.ui.movies.list

import androidx.lifecycle.ViewModel
import humbertocosta.com.omdbchallenge.data.repository.MoviesRepository
import humbertocosta.com.omdbchallenge.internal.lazyDeferred


class SearchMoviesListViewModel(private val moviesRepository: MoviesRepository, title: String) : ViewModel() {
    val searchEntries by lazyDeferred {
        moviesRepository.searchMoviesByTitle(title)
    }
}