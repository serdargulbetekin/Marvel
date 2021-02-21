package com.foreks.android.marvel.modules.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.foreks.android.marvel.databinding.ActivityDetailBinding
import com.foreks.android.marvel.modules.factory.ViewModelFactory

class DetailActivity : AppCompatActivity() {
    private val viewBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    private lateinit var viewModel: DetailActivityViewModel
    private val factory by lazy { ViewModelFactory() }

    private val adapter by lazy {
        DetailActivityAdapter()
    }

    private val extrasCharacterId by lazy {
        intent?.getStringExtra(EXTRAS_CHARACTER_ID) ?: "-"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewModel = ViewModelProvider(this, factory).get(DetailActivityViewModel::class.java)

        viewBinding.apply {
            marvelToolbar.show(extrasCharacterId, showBack = { onBackPressed() })
            recyclerView.layoutManager =
                LinearLayoutManager(this@DetailActivity)
            recyclerView.adapter = adapter

        }
        viewModel.getComics(extrasCharacterId)
        viewModel.charactersLiveData.observe(this, Observer {
            adapter.updateList(it)
        })

    }

    companion object {
        private const val EXTRAS_CHARACTER_ID = "EXTRAS_CHARACTER_ID"

        fun createIntent(context: Context, characterId: String) =
            Intent(context, DetailActivity::class.java).apply {
                putExtra(EXTRAS_CHARACTER_ID, characterId)
            }
    }
}