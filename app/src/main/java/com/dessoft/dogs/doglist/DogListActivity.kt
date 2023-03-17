package com.dessoft.dogs.doglist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.dessoft.dogs.DogListViewModel
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.databinding.ActivityDogListBinding
import com.dessoft.dogs.dogdetail.DogDetailActivity
import com.dessoft.dogs.dogdetail.DogDetailActivity.Companion.DOG_KEY

class DogListActivity : AppCompatActivity() {

    private val dogListViewModel: DogListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDogListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pb = binding.pbDogList

        val recycler = binding.rvDogRecycler
        recycler.layoutManager = LinearLayoutManager(this)

        val adapter = DogAdapter()

        adapter.setOnItemClickListener {
            //pasar el dog a dogDetailActivity
            val intent = Intent(this, DogDetailActivity::class.java)
            intent.putExtra(DOG_KEY, it)
            startActivity(intent)
        }

        recycler.adapter = adapter

        dogListViewModel.dogList.observe(this) { doglist ->
            adapter.submitList(doglist)
        }

        dogListViewModel.status.observe(this) { status ->
            when (status) {
                ApiResponseStatus.LOADING -> { //mostrar progressBar
                    pb.visibility = View.VISIBLE
                }
                ApiResponseStatus.ERROR -> { //mostrar Toast Y ocultar el progressBar
                    pb.visibility = View.GONE
                    Toast.makeText(this, "Error al descargar los datos", Toast.LENGTH_LONG).show()
                }
                ApiResponseStatus.SUCCESS -> { //ocultar el progressBar
                    pb.visibility = View.GONE
                }
                else -> {
                    //ocultar el progressBar
                    pb.visibility = View.GONE
                    Toast.makeText(this, "Status desconocido", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

}