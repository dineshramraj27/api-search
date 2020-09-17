package com.example.searchkeyword.adapter

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchkeyword.R
import com.example.searchkeyword.model.AlbumItem
import com.example.searchkeyword.model.ArtistItem
import com.example.searchkeyword.model.TrackItem
import com.example.searchkeyword.ui.MainFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

class ListAdapter(albumResponse: com.example.searchkeyword.model.Response, mainFragment: MainFragment) :
    RecyclerView.Adapter<MovieViewHolder>() {

    // list of album item obj
    private var albumList = albumResponse.results?.albummatches?.album
    // list of artist item obj
    private var artistList = albumResponse.results?.artistmatches?.artist
    // list of songs item obj
    private var trackList = albumResponse.results?.trackmatches?.track
    //
    private val large = "large"

    private val onItemClickedListener = mainFragment as OnItemClickedListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(
            inflater,
            parent
        )
    }

    // bind the view holder data on its position
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val albumItem: AlbumItem? = albumList?.get(position)
        val trackItem: TrackItem? = trackList?.get(position)
        val artistItem: ArtistItem? = artistList?.get(position)
        when {
            albumItem != null -> {
                holder.bind(albumItem.name.toString(), albumItem.artist.toString())
                val images = albumItem.image
                for (index in 1..3) {
                    if (images?.get(index)?.size!! == large && !TextUtils.isEmpty(images?.get(index)?.text)) {
                        Picasso.with(holder.itemView.context).load(images?.get(index)?.text)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(holder.itemView.image_view)
                    }
                }

                // on click of an item, the listener gets triggered
                holder.itemView.setOnClickListener {
                    onItemClickedListener.onItemSelected(albumItem.url.toString())
                }
            }
            artistItem != null -> {
                holder.bind(artistItem.name.toString(), artistItem.mbid.toString())
                val images = artistItem.image

                for (index in 1..3) {
                    if (images?.get(index)?.size!! == large && !TextUtils.isEmpty(images?.get(index)?.text)) {
                        Picasso.with(holder.itemView.context).load(images?.get(index)?.text)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(holder.itemView.image_view)
                    }
                }

                // on click of an item, the listener gets triggered
                holder.itemView.setOnClickListener {
                    onItemClickedListener.onItemSelected(artistItem.url.toString())
                }
            }
            // track item details
            trackItem != null -> {
                holder.bind(trackItem.name.toString(), trackItem.artist.toString())
                val images = trackItem.image

                for (index in 1..3) {
                    if (images?.get(index)?.size!! == large && !TextUtils.isEmpty(images?.get(index)?.text)) {
                        Picasso.with(holder.itemView.context).load(images?.get(index)?.text)
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(holder.itemView.image_view)
                    }
                }

                // on click of an item, the listener gets triggered
                holder.itemView.setOnClickListener {
                    onItemClickedListener.onItemSelected(trackItem.url.toString())
                }
            }
        }

    }

    // returns an item list count
    override fun getItemCount(): Int {
        return when {
            albumList != null -> {
                albumList?.size!!
            }
            artistList != null -> {
                artistList?.size!!
            }
            trackList != null -> {
                trackList?.size!!
            }
            else -> 0
        }
    }

}

class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {
    private var mTitleView: TextView? = null
    private var mYearView: TextView? = null
    private var imageView: ImageView? = null

    // initializing the view items
    init {
        mTitleView = itemView.findViewById(R.id.name)
        mYearView = itemView.findViewById(R.id.artist)
        imageView = itemView.findViewById(R.id.image_view)
    }

    // binding the item data in a views
    fun bind(name: String, artist: String) {
        mTitleView?.text = name
        mYearView?.text = artist
    }

}

interface OnItemClickedListener {
    fun onItemSelected(url: String)
}