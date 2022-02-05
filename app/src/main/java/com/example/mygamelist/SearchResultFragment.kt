package com.example.mygamelist

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mygamelist.databinding.FragmentSearchPageBinding
import com.example.mygamelist.databinding.FragmentSearchResultBinding
import com.example.mygamelist.databinding.ItemListGameBinding
import com.example.mygamelist.response.Games

class SearchResultFragment : Fragment() {
    private lateinit var binding : FragmentSearchResultBinding
    private lateinit var listResult : ArrayList<Games>

    companion object{
        val EXTRA_GAMES_RESULT = "extra_games_result"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(arguments != null) {
            listResult = this.arguments?.getParcelableArrayList<Games>(EXTRA_GAMES_RESULT) as ArrayList<Games>

            if(listResult.size == 0) {
                binding.tvResult.text = "No Games Found"
            } else {
                binding.tvResult.text = "${listResult.size} Games Found"
                val rvResult = binding.rvSearchResult
                // set rv
                rvResult.setHasFixedSize(true)
                rvResult.layoutManager = GridLayoutManager(this.context, 1, GridLayoutManager.HORIZONTAL, false)
                val gridGameAdapter = GridGameAdapter(listResult)
                rvResult.adapter = gridGameAdapter

                gridGameAdapter.setOnItemClickCallback(object : GridGameAdapter.OnItemClickCallback{
                    override fun onItemClicked(data: Games) {
                        showSelectedGames(data)
                    }
                })
            }
        }
    }

    private fun showSelectedGames(game : Games) {
        val moveIntent = Intent(this.context, DetailActivity::class.java)
        moveIntent.putExtra(DetailActivity.EXTRA_GAME, game)
        startActivity(moveIntent)
    }
}