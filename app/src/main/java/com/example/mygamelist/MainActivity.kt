package com.example.mygamelist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.mygamelist.api.RetrofitClient
import com.example.mygamelist.databinding.ActivityMainBinding
import com.example.mygamelist.response.Games
import com.example.mygamelist.response.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var newGameList : ArrayList<Games> = arrayListOf()
    private val topGameList : ArrayList<Games> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get games data
        RetrofitClient().instance.getNewestGames().enqueue(object : Callback<PostResponse>{
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                val postResponse : PostResponse? = response.body()
                postResponse?.results?.let { newGameList.addAll(it) }

                RetrofitClient().instance.getTopGames().enqueue(object  : Callback<PostResponse>{
                    override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                        val postResponse2 : PostResponse? = response.body()
                        postResponse2?.results?.let { topGameList.addAll(it) }

                        Log.d("main - newg", newGameList.size.toString())
                        Log.d("main - topg", topGameList.size.toString())

                        if(binding.botNav.menu.getItem(0).isChecked) {
                            val fragment = HomePageFragment()
                            val bundle = Bundle()
                            bundle.putParcelableArrayList(HomePageFragment.EXTRA_NEW_GAMES, newGameList)
                            bundle.putParcelableArrayList(HomePageFragment.EXTRA_TOP_GAMES, topGameList)
                            fragment.arguments = bundle
                            loadFragment(fragment)
                        }
                    }
                    override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Failed To Get Top Rated Games", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Failed To Get New Released Games", Toast.LENGTH_SHORT).show()
            }
        })


        binding.botNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.menu_page_home -> {
                    if(!binding.botNav.menu.getItem(0).isChecked) {
                        val fragment = HomePageFragment()
                        val bundle = Bundle()
                        bundle.putParcelableArrayList(HomePageFragment.EXTRA_NEW_GAMES, newGameList)
                        bundle.putParcelableArrayList(HomePageFragment.EXTRA_TOP_GAMES, topGameList)
                        fragment.arguments = bundle
                        loadFragment(fragment)
                    }
                    return@setOnItemSelectedListener true
                }
                R.id.menu_page_search -> {
                    if(!binding.botNav.menu.getItem(1).isChecked) {
                        loadFragment(SearchPageFragment())
                    }
                    return@setOnItemSelectedListener true
                }
                R.id.menu_page_account -> {
                    if(!binding.botNav.menu.getItem(2).isChecked) {
                        loadFragment(AccountPageFragment())
                    }
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val fragmentManager : FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(binding.fragmentContainer.id, fragment, HomePageFragment::class.java.simpleName)
            commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if(id == R.id.btnSettings) {
            val moveIntent = Intent(this, SettingsActivity::class.java)
            startActivity(moveIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}