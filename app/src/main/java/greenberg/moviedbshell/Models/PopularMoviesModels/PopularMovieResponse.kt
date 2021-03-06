package greenberg.moviedbshell.Models.PopularMoviesModels

import com.google.gson.annotations.SerializedName

data class PopularMovieResponse(
	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: MutableList<PopularMovieResultsItem?>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)