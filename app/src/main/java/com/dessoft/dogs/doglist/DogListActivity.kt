package com.dessoft.dogs.doglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.dessoft.dogs.DogListViewModel
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.databinding.ActivityDogListBinding
import com.dessoft.dogs.dogdetail.DogDetailActivity
import com.dessoft.dogs.dogdetail.DogDetailActivity.Companion.DOG_KEY

private const val GRID_SPAN_COUNT = 3

class DogListActivity : AppCompatActivity() {

    private val dogListViewModel: DogListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pb = binding.pbDogList

        val recycler = binding.rvDogRecycler
        //recycler.layoutManager = LinearLayoutManager(this)
        recycler.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)

        val adapter = DogAdapter()

        adapter.setOnItemClickListener {
            //pasar el dog a dogDetailActivity
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }

        /*//Se quita en la clase 58, porque ya no se agregaran los perros desde aca
        adapter.setLongOnItemClickListener {
            dogListViewModel.addDogToUser(it.id)
        }*/

        recycler.adapter = adapter

        dogListViewModel.dogList.observe(this) { doglist ->
            adapter.submitList(doglist)
        }

        dogListViewModel.status.observe(this) { status ->

            when (status) {
                is ApiResponseStatus.Error -> {
                    //mostrar Toast Y ocultar el progressBar
                    pb.visibility = View.GONE
                    Toast.makeText(this, status.messageId, Toast.LENGTH_LONG).show()
                }
                is ApiResponseStatus.Loading -> {
                    //mostrar progressBar
                    pb.visibility = View.VISIBLE
                }
                is ApiResponseStatus.Success -> {
                    //ocultar el progressBar
                    pb.visibility = View.GONE
                }
            }
        }

    }

}