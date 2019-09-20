package humbertocosta.com.omdbchallenge.ui.movies.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import humbertocosta.com.omdbchallenge.data.network.Status
import humbertocosta.com.omdbchallenge.data.network.response.MovieDetailsResponse
import humbertocosta.com.omdbchallenge.internal.glide.GlideApp
import humbertocosta.com.omdbchallenge.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.movie_details_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeFormatterBuilder




class MovieDetailsFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactoryInstanceFactory
            : ((String) -> MovieDetailsViewModelFactory) by factory()

    private lateinit var viewModel: MovieDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(humbertocosta.com.omdbchallenge.R.layout.movie_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let { MovieDetailsFragmentArgs.fromBundle(it) }

        viewModel = ViewModelProviders.of(this, viewModelFactoryInstanceFactory(safeArgs!!.imdbID))
            .get(MovieDetailsViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val movieDetails = viewModel.movieDetails.await()

        movieDetails.observe(this@MovieDetailsFragment, Observer { response ->
            if (response == null) return@Observer
            if (response.status == Status.ERROR) {
                Toast.makeText(this@MovieDetailsFragment.context, response.message, Toast.LENGTH_LONG).show()
                return@Observer
            }

            group_loading.visibility = View.GONE

            val movieDetails = response.data!!

            updateTitle(movieDetails.title)
            updatePoster(movieDetails.poster)
            updateMovieInfo(movieDetails)
            updateIsMovieReleased(movieDetails.released)
        })
    }

    private fun updateTitle(title: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = null

        textView_title.text = title
    }

    private fun updatePoster(poster: String) {
        GlideApp.with(this@MovieDetailsFragment)
            .load(poster)
            .into(imageView_poster)
    }

    private fun updateMovieInfo(movieDetails: MovieDetailsResponse) {
        textView_releaseDate.text = movieDetails.released
        textView_imdbRating.text = movieDetails.imdbRating + " / 10.0"
        textView_duration.text = movieDetails.runtime
        textView_genre.text = movieDetails.genre
        textView_director.text = movieDetails.director
        textView_actors.text = movieDetails.actors
        textView_production.text = movieDetails.production
        textView_plot.text = movieDetails.plot
        textView_awards.text = movieDetails.awards
    }

    private fun updateIsMovieReleased(released: String) {
        if (released == "N/A") return
        val formatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ofPattern("dd MMM yyyy"))
            .toFormatter()
        val isStillToBeReleased = LocalDate.parse(released, formatter).isAfter(LocalDate.now())
        group_comingSoon.visibility = if (isStillToBeReleased) View.VISIBLE else View.GONE
    }
}