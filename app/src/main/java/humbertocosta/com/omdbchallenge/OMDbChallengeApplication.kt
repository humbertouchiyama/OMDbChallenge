package humbertocosta.com.omdbchallenge

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import humbertocosta.com.omdbchallenge.data.network.*
import humbertocosta.com.omdbchallenge.data.repository.MoviesRepository
import humbertocosta.com.omdbchallenge.data.repository.MoviesRepositoryImpl
import humbertocosta.com.omdbchallenge.ui.movies.details.MovieDetailsViewModelFactory
import humbertocosta.com.omdbchallenge.ui.movies.list.SearchMoviesListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.factory
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class OMDbChallengeApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@OMDbChallengeApplication))

        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { OMDbApiService(instance()) }
        bind<MoviesNetworkDataSource>() with singleton { MoviesNetworkDataSourceImpl(instance()) }
        bind<MoviesRepository>() with singleton { MoviesRepositoryImpl(instance()) }
        bind() from factory { title: String -> SearchMoviesListViewModelFactory(instance(), title) }
        bind() from factory { imdbID: String -> MovieDetailsViewModelFactory(instance(), imdbID) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}