package com.foreks.android.marvel.modules.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.foreks.android.marvel.databinding.ActivityMainBinding
import com.foreks.android.marvel.modules.detail.DetailActivity
import com.foreks.android.marvel.modules.factory.ViewModelFactory
import com.foreks.android.marvel.modules.repo.MarvelCharacter

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private val factory by lazy { ViewModelFactory() }
    private val viewBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val adapter by lazy {
        MainActivityAdapter {
            redirectToDetail(it)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)

        viewBinding.apply {
            marvelToolbar.show("MARVEL")
            recyclerView.layoutManager =
                LinearLayoutManager(this@MainActivity)
            recyclerView.adapter = adapter

        }
        viewModel.charactersLiveData.observe(this, Observer {
            adapter.updateList(it)
        })

    }

    private fun redirectToDetail(marvelCharacter: MarvelCharacter) {
        startActivity(DetailActivity.createIntent(this, marvelCharacter.id))
    }
}