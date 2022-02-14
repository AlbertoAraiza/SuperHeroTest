package mx.araiza.superherotest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.araiza.superherotest.model.SuperheroModel

class SuperheroDetailsViewmodel :ViewModel(){
    val superhero =MutableLiveData<SuperheroModel>()
}