package humbertocosta.com.omdbchallenge.ui.movies.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import humbertocosta.com.omdbchallenge.R
import humbertocosta.com.omdbchallenge.data.model.SearchEntry
import humbertocosta.com.omdbchallenge.data.network.Status
import humbertocosta.com.omdbchallenge.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.search_movies_list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SearchMoviesListFragment  : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: SearchMoviesListViewModelFactory by instance()

    private val groupAdapter = GroupAdapter<ViewHolder>()
    private lateinit var viewModel: SearchMoviesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        android.R.dimen.app_icon_size
        return inflater.inflate(R.layout.search_movies_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SearchMoviesListViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        updateTitle()

        button_search.setOnClickListener {
            val searchTerm = editText_search.text.toString()
            if (searchTerm == null || searchTerm.isEmpty()) return@setOnClickListener

            group_loading.visibility = View.VISIBLE

            viewModel.updateSearchTerm(searchTerm)
        }

        initRecyclerView()

        viewModel.getMovies().observe(this@SearchMoviesListFragment, Observer { response ->
            if (response == null) return@Observer
            if (response.status == Status.ERROR) {
                Toast.makeText(this@SearchMoviesListFragment.context, response.message, Toast.LENGTH_LONG).show()
                return@Observer
            }

            root.hideKeyboard()

            group_loading.visibility = View.GONE

            val searchResponse = response.data!!

            if (searchResponse.searchEntries == null || searchResponse.searchEntries.isEmpty()) {
                groupAdapter.clear()
                Toast.makeText(this@SearchMoviesListFragment.context, "No entry found.", Toast.LENGTH_LONG).show()
                return@Observer
            }

            populateAdapter(searchResponse.searchEntries.toMovieItems())
        })
    }

    private fun List<SearchEntry>.toMovieItems() : List<MovieItem> {
        return this.map {
            MovieItem(it)
        }
    }

    private fun initRecyclerView() {
        groupAdapter.apply {
            spanCount = 2
        }

        recyclerView.apply {
            layoutManager = GridLayoutManager(this@SearchMoviesListFragment.context, groupAdapter.spanCount)
            adapter = groupAdapter
        }
        groupAdapter.setOnItemClickListener { item, view ->
            (item as? MovieItem)?.let {
                showWeatherDetail(it.movieEntry.imdbID, view)
            }
        }
    }

    private fun populateAdapter(items: List<MovieItem>) {
        groupAdapter.clear()
        groupAdapter.addAll(items)
    }

    private fun showWeatherDetail(imdbID: String, view: View) {
        val actionDetail = SearchMoviesListFragmentDirections.actionDetail(imdbID)
        Navigation.findNavController(view).navigate(actionDetail)
    }

    private fun updateTitle() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.app_name)
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}