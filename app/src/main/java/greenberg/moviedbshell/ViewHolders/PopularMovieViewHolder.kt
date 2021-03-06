package greenberg.moviedbshell.ViewHolders

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import greenberg.moviedbshell.Models.PopularMoviesModels.PopularMovieResultsItem
import greenberg.moviedbshell.MosbyImpl.MovieDetailFragment
import greenberg.moviedbshell.R
import java.text.SimpleDateFormat

class PopularMovieAdapter(var popularMovieList: MutableList<PopularMovieResultsItem?>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PopularMovieViewHolder) {
            val currentItem = popularMovieList?.get(position)
            //todo: load posters and have like, placeholders
            holder.cardItemTitle.text = currentItem?.title
            holder.cardItemReleaseDate.text = currentItem?.releaseDate?.let { processReleaseDate(it) }
            holder.cardItemOverview.text = currentItem?.overview
            popularMovieList?.get(position)?.let { fetchPoster(holder.cardItemPosterImage, it) }
            //TODO: probably move this to the activity
            holder.cardItem.setOnClickListener {
                val fragment = MovieDetailFragment()
                val bundle = Bundle()
                bundle.putInt("MovieID", currentItem?.id ?: -1)
                fragment.arguments = bundle
                //Perhaps move into setting an onclick listener somewhere else and having the adapter just set it.
                (it.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        //TODO: is this for the best?
                        .addToBackStack(MovieDetailFragment.TAG)
                        .commit()
            }
        }
    }

    override fun getItemCount() = popularMovieList?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PopularMovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.popular_movie_card, parent, false))
    }

    //TODO: is this context check ok
    private fun fetchPoster(cardItemPosterView: ImageView, item: PopularMovieResultsItem) {
        //Load poster art
        item.posterPath?.let {
            Glide.with(cardItemPosterView)
                    .load(cardItemPosterView.context.getString(R.string.poster_url_substitution, it))
                    .apply { RequestOptions().centerCrop() }
                    .into(cardItemPosterView)
        }
    }

    //TODO: probably make sure every date is like this?
    //there has to be a better way to do this
    private fun processReleaseDate(releaseDate: String): String {
        return if (releaseDate.isNotBlank()) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = inputFormat.parse(releaseDate)
            val outputFormat = SimpleDateFormat("MM/dd/yyyy")
            outputFormat.format(date)
        } else {
            return ""
        }
    }

    class PopularMovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var cardItemPosterImage: ImageView = view.findViewById(R.id.cardItemPosterImage)
        var cardItemTitle: TextView = view.findViewById(R.id.cardItemTitle)
        var cardItemReleaseDate: TextView = view.findViewById(R.id.cardItemReleaseDate)
        var cardItemOverview: TextView = view.findViewById(R.id.cardItemOverview)
        var cardItem: CardView = view.findViewById(R.id.popularMovieCard)
    }
}