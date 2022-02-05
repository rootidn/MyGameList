package com.example.mygamelist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mygamelist.databinding.ItemListGameBinding
import com.example.mygamelist.response.Games

class GridGameAdapter(private val listGame : ArrayList<Games>) : RecyclerView.Adapter<GridGameAdapter.GridViewHolder>() {
//    private lateinit var binding: ItemListGameBinding
    private lateinit var onItemClickCallback : OnItemClickCallback

    class GridViewHolder(val binding: ItemListGameBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val binding = ItemListGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val game = listGame[position]
        Glide.with(holder.itemView.context)
            .load(game.background_image)
            .into(holder.binding.imgItemPhoto)
        holder.binding.tvItemTitle.text = game.name
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(listGame[position])
        }
    }

    override fun getItemCount(): Int = listGame.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Games)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
}