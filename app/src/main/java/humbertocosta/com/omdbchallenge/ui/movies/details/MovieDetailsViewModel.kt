package humbertocosta.com.omdbchallenge.ui.movies.details

import androidx.lifecycle.ViewModel
import humbertocosta.com.omdbchallenge.data.repository.MoviesRepository
import humbertocosta.com.omdbchallenge.internal.lazyDeferred

class MovieDetailsViewModel(private val moviesRepository: MoviesRepository, imdbID: String) : ViewModel() {
    val movieDetails by lazyDeferred {
        moviesRepository.getMovieDetailsById(imdbID)
    }

    val isMovieReleased by lazyDeferred {
        moviesRepository.checkIfMovieIsReleased()
    }
}