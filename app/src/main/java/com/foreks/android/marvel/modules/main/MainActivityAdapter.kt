package com.foreks.android.marvel.modules.main


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foreks.android.marvel.databinding.RowMainBinding
import com.foreks.android.marvel.modules.repo.MarvelCharacter

class MainActivityAdapter(
    private val onItemClick: (MarvelCharacter) -> Unit
) :
    RecyclerView.Adapter<MainViewHolder>() {


    private val items = mutableListOf<MarvelCharacter>()

    fun updateList(marvelCharacterList: List<MarvelCharacter>) {
        items.clear()
        items.addAll(marvelCharacterList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            RowMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindItem(items[position], onItemClick)
    }

}

class MainViewHolder(private val binding: RowMainBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bindItem(
        marvelCharacter: MarvelCharacter,
        onItemClick: (MarvelCharacter) -> Unit
    ) {
        binding.textViewName.text =
            marvelCharacter.name

        Glide.with(binding.root.context)
            .load(marvelCharacter.image)
            .centerCrop()
            .into(binding.imageView)
        binding.root.setOnClickListener {
            onItemClick.invoke(marvelCharacter)
        }
    }
}
