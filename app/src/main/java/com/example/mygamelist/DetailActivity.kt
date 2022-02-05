package com.example.mygamelist

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mygamelist.databinding.ActivityDetailBinding
import com.example.mygamelist.response.Games

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding

    companion object{
        const val EXTRA_GAME = "extra_game"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val game : Games? = intent.getParcelableExtra(EXTRA_GAME)

        if (game != null) {
            binding.tvGameTitle.text = game.name
            Glide.with(this)
                .load(game.background_image)
                .into(binding.imgGamePhoto)
            binding.tvReleased.text = game.released
            binding.tvRatings.text = game.rating.toString()
            //recycler view grid genre
            val rvGenre = binding.rvGenre
            rvGenre.setHasFixedSize(true)
            rvGenre.layoutManager = GridLayoutManager(this, 3)
            val listGenreAdapter = ListGenreAdapter(game.genres)
            rvGenre.adapter = listGenreAdapter

            binding.btnShare.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, "Lets play \"${game.name}\" with me," +
                        "\nCheck games description on RAWG - MyGamesList App!" +
                        "\nDownload it From PlayStore!")
                startActivity(Intent.createChooser(intent, "Share To :"))
            }

            binding.btnMoreDetails.setOnClickListener {
                val moveIntent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://rawg.io/games/"+game.slug))
                startActivity(moveIntent)
            }
        }
    }
}