package com.dessoft.dogs

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

class DogListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var dogCoroutineRule = DogCoroutineRule()


}