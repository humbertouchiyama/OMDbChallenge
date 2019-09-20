package humbertocosta.com.omdbchallenge.ui.movies.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import humbertocosta.com.omdbchallenge.data.repository.MoviesRepository

class SearchMoviesListViewModelFactory(
    private val moviesRepository: MoviesRepository,
    private val title: String
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchMoviesListViewModel(moviesRepository, title) as T
    }
}