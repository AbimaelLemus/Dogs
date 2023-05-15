package com.dessoft.dogs.doglist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import coil.annotation.ExperimentalCoilApi
import com.dessoft.dogs.dogdetail.DogDetailComposeActivity
import com.dessoft.dogs.dogdetail.ui.theme.DogsTheme
import com.dessoft.dogs.model.Dog
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
/*AndroidEntryPoint, es parte de la injeccion de dependencias*/
@AndroidEntryPoint
class DogListActivity : ComponentActivity() {

    // se quita en el video 86 porque hilt lo va a proveer desde el viewModel
    //private val viewModel: DogListViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogsTheme {
                DogListScreen(
                    onNavigationIconClick = ::onNavigationIconClick,
                    onDogClicked = ::openDogDetailActivity,
                )
            }
        }
    }

    /*
    //Se quita en el video 86
    private fun resetApiResponseStatus() {
        viewModel.resetApiResponseStatus()
    }*/

    private fun openDogDetailActivity(dog: Dog) {
        //pasar el dog a dogDetailActivity
        val intent = Intent(this, DogDetailComposeActivity::class.java)
        intent.putExtra(DogDetailComposeActivity.DOG_KEY, dog)
        startActivity(intent)
    }

    private fun onNavigationIconClick() {
        finish()
    }


}

/*val binding = ActivityDogListBinding.inflate(layoutInflater)
setContentView(binding.root)

val pb = binding.pbDogList

val recycler = binding.rvDogRecycler
//recycler.layoutManager = LinearLayoutManager(this)
recycler.layoutManager = GridLayoutManager(this, GRID_SPAN_COUNT)

val adapter = DogAdapter()

adapter.setOnItemClickListener {
    //pasar el dog a dogDetailActivity
    val intent = Intent(this, DogDetailComposeActivity::class.java)
    intent.putExtra(DogDetailComposeActivity.DOG_KEY, it)
    startActivity(intent)
}

*//*//*/Se quita en la clase 58, porque ya no se agregaran los perros desde aca
        adapter.setLongOnItemClickListener {
            dogListViewModel.addDogToUser(it.id)
        }*//*

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
        }*/