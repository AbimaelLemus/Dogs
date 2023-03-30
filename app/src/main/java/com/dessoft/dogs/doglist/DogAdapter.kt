package com.dessoft.dogs.doglist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dessoft.dogs.R
import com.dessoft.dogs.databinding.DogListItemBinding
import com.dessoft.dogs.model.Dog

class DogAdapter : ListAdapter<Dog, DogAdapter.DogViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Dog>() {
        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
            return oldItem.id == newItem.id

        }

    }

    private var onItemClickListener: ((Dog) -> Unit)? = null
    fun setOnItemClickListener(onItemClickListener: (Dog) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    private var onLongItemClickListener: ((Dog) -> Unit)? = null
    fun setLongOnItemClickListener(onLongItemClickListener: (Dog) -> Unit) {
        this.onLongItemClickListener = onLongItemClickListener
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

            if (dog.inCollection) {
                binding.dogListItemLayout.background = ContextCompat.getDrawable(
                    binding.dogImage.context,
                    R.drawable.dog_list_item_background_stroke
                )

                binding.tvIdDog.visibility = View.GONE
                binding.dogImage.visibility = View.VISIBLE

                binding.dogListItemLayout.setOnClickListener {
                    //para invocar, y si no es null entonces invoca al perro
                    onItemClickListener?.invoke(dog)
                }

                binding.dogImage.load(dog.imageUrl)
                binding.tvNameDog.text = dog.nameEs
                binding.tvIndexDog.visibility = View.VISIBLE
                binding.tvIndexDog.text = dog.index.toString()
            } else {
                binding.tvIdDog.visibility = View.VISIBLE
                binding.dogImage.visibility = View.GONE
                binding.tvIndexDog.visibility = View.GONE
                binding.tvIdDog.text = dog.index.toString()
                binding.dogListItemLayout.background = ContextCompat.getDrawable(
                    binding.dogImage.context,
                    R.drawable.dog_list_item_null_background
                )

                binding.dogListItemLayout.setOnLongClickListener {
                    onLongItemClickListener?.invoke(dog)
                    true
                }
            }
        }

    }


}