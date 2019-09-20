package humbertocosta.com.omdbchallenge.ui.movies.list

import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import humbertocosta.com.omdbchallenge.R
import humbertocosta.com.omdbchallenge.data.model.SearchEntry
import humbertocosta.com.omdbchallenge.internal.glide.GlideApp
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieItem(val movieEntry: SearchEntry) : Item() {
    override fun getLayout() = R.layout.item_movie

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            viewHolder.root.textView_title.text = movieEntry.title
            updatePosterImage()
        }
    }

    private fun ViewHolder.updatePosterImage() {
        GlideApp.with(this.containerView)
            .load(movieEntry.poster)
            .into(root.imageView_poster)
    }
}