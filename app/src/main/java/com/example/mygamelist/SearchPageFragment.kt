package com.example.mygamelist

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mygamelist.api.RetrofitClient
import com.example.mygamelist.databinding.FragmentSearchPageBinding
import com.example.mygamelist.response.Games
import com.example.mygamelist.response.PostResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPageFragment : Fragment() {
    private lateinit var binding : FragmentSearchPageBinding
    private var listResult : ArrayList<Games> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.edtSearch.setOnKeyListener(object : View.OnKeyListener{
            override fun onKey(view: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if(event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (view != null) {
                        listResult = arrayListOf()
                        val keyword = binding.edtSearch.text

                        RetrofitClient().instance.getSearchGames(keyword.toString()).enqueue(object  :
                            Callback<PostResponse> {
                            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                                val postResponse : PostResponse? = response.body()
                                postResponse?.results?.let { listResult.addAll(it) }

                                val searchResultFragment =SearchResultFragment()
                                val bundle = Bundle()
                                bundle.putParcelableArrayList(SearchResultFragment.EXTRA_GAMES_RESULT, listResult)
                                searchResultFragment.arguments = bundle

                                val fragmentManager = childFragmentManager
                                fragmentManager.beginTransaction()
                                    .replace(binding.containerResult.id, searchResultFragment, SearchResultFragment::class.java.simpleName)
                                    .commit()
                            }
                            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                                Toast.makeText(view.context, "Failed To Get Top Rated Games", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                    return true
                } else {
                    return false
                }
            }
        })
    }
}