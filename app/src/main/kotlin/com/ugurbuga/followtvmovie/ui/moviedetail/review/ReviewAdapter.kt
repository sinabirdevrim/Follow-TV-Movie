package com.ugurbuga.followtvmovie.ui.moviedetail.review

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ugurbuga.followtvmovie.base.adapter.FTMBaseListAdapter
import com.ugurbuga.followtvmovie.base.adapter.ListAdapterItem
import com.ugurbuga.followtvmovie.domain.moviedetail.model.review.ReviewUIModel

class ReviewAdapter : FTMBaseListAdapter<ListAdapterItem>(
    itemsSame = { old, new -> old == new },
    contentsSame = { old, new -> old == new }
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return ReviewViewHolder(parent, inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ReviewViewHolder) {
            val item = getItem(position) as ReviewUIModel
            holder.bind(item)
        }
    }
}
