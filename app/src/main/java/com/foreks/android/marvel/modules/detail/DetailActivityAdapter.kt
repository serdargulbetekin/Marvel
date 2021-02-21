package com.foreks.android.marvel.modules.detail


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foreks.android.marvel.databinding.RowDetailBinding
import com.foreks.android.marvel.modules.repo.Comics

class DetailActivityAdapter() :
    RecyclerView.Adapter<DetailViewHolder>() {

    private val items = mutableListOf<Comics>()

    fun updateList(comicsList: List<Comics>) {
        items.clear()
        items.addAll(comicsList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            RowDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bindItem(items[position])
    }

}

class DetailViewHolder(private val binding: RowDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bindItem(
        comics: Comics
    ) {
        binding.textViewName.text = comics.title
        binding.textViewDescription.text = comics.description
        binding.textViewDate.text = comics.date.toString()

        Glide.with(binding.root.context)
            .load(comics.image)
            .centerCrop()
            .into(binding.imageView)
    }
}
