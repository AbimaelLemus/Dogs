package com.dessoft.dogs.doglist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dessoft.dogs.Dog
import com.dessoft.dogs.databinding.DogListItemBinding

class DogAdapter : ListAdapter<Dog, DogAdapter.DogViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.id == newItem.id

        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DogViewHolder {
        val binding = DogListItemBinding.inflate(LayoutInflater.from(parent.context))

        return DogViewHolder(binding)

    }

    override fun onBindViewHolder(dogViewHolder: DogViewHolder, position: Int) {
        val dog = getItem(position)
        dogViewHolder.bind(dog)

    }

    inner class DogViewHolder(val binding: DogListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dog: Dog) {
            binding.tvDogName.text = dog.nameEs
        }

    }


}