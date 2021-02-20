package com.foreks.android.marvel.modules.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.foreks.android.marvel.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private val viewBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    private val extrasCharacterId by lazy {
        intent?.getStringExtra(EXTRAS_CHARACTER_ID) ?: "-"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }

    companion object {
        private const val EXTRAS_CHARACTER_ID = "EXTRAS_CHARACTER_ID"

        fun createIntent(context: Context, characterId: String) = Intent().apply {
            putExtra(EXTRAS_CHARACTER_ID, characterId)
        }
    }
}