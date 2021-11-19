package com.fitz.hiltdemo.usecase.databridge

import com.fitz.hiltdemo.usecase.model.MovieResult
import com.fitz.hiltdemo.usecase.model.MovieViewItem
import com.fitz.hiltdemo.usecase.model.RepositoryResult
import javax.inject.Inject

class MovieDataBridge @Inject constructor(): DataBridge<MovieViewItem> {

    override fun getData(): MovieResult {
        return MovieResult(mockList(), RepositoryResult.DataOperation.LOADED)
    }

    private fun mockList() : MutableList<MovieViewItem> {
        return mutableListOf(
            MovieViewItem(title = "The Lion King", imageURL = "https://lumiere-a.akamaihd.net/v1/images/p_thelionking_19752_1_0b9de87b.jpeg", movieRating = 9.6),
            MovieViewItem(title = "The Day the Earth Stood Still", imageURL = "https://flxt.tmsimg.com/assets/p176378_p_v13_ay.jpg", movieRating = 4.0)
        )
    }

    override fun editEntity(entity: MovieViewItem) {
        TODO("Not yet implemented")
    }
}