package com.example.mygamelist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mygamelist.databinding.FragmentHomePageBinding
import com.example.mygamelist.response.Games

class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomePageBinding
    private var newGameList = ArrayList<Games>()
    private var topGameList = ArrayList<Games>()

    companion object{
        val EXTRA_NEW_GAMES = "extra_new_games"
        val EXTRA_TOP_GAMES = "extra_top_games"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            newGameList = this.arguments?.getParcelableArrayList<Games>(EXTRA_NEW_GAMES) as ArrayList<Games>
            topGameList = this.arguments?.getParcelableArrayList<Games>(EXTRA_TOP_GAMES) as ArrayList<Games>
        }
        // new release games
        val rvNewGames : RecyclerView = binding.rvNewGame
        showRecyclerView(rvNewGames, newGameList)
        // top rated games
        val rvTopGames = binding.rvTopGame
        showRecyclerView(rvTopGames, topGameList)
    }

    private fun showRecyclerView(rvTarget : RecyclerView, gameList : ArrayList<Games>){
        rvTarget.setHasFixedSize(true)
        rvTarget.layoutManager = GridLayoutManager(this.context, 1, GridLayoutManager.HORIZONTAL, false)
        val gridNewGameAdapter = GridGameAdapter(gameList)
        rvTarget.adapter = gridNewGameAdapter
        gridNewGameAdapter.setOnItemClickCallback(object : GridGameAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Games) {
                showSelectedGames(data)
            }
        })
    }
    private fun showSelectedGames(game : Games) {
        val moveIntent = Intent(this.context, DetailActivity::class.java)
        moveIntent.putExtra(DetailActivity.EXTRA_GAME, game)
        startActivity(moveIntent)
    }
}