package com.example.mygamelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mygamelist.databinding.ItemListGenreBinding
import com.example.mygamelist.response.Genres

class ListGenreAdapter(val listGenre : ArrayList<Genres>) : RecyclerView.Adapter<ListGenreAdapter.ListGenreViewHolder>() {
    class ListGenreViewHolder(var binding: ItemListGenreBinding) : RecyclerView.ViewHolder(binding.root) {
        // pass
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListGenreViewHolder {
        val binding = ItemListGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListGenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListGenreViewHolder, position: Int) {
        holder.binding.tvGenre.text = listGenre[position].name
    }

    override fun getItemCount(): Int = listGenre.size
}