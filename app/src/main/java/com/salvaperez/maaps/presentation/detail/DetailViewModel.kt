package com.salvaperez.maaps.presentation.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.salvaperez.maaps.presentation.util.Resource
import com.salvaperez.maaps.presentation.view_model.TransportsViewModel

class DetailViewModel: ViewModel(){

    val placeData = MutableLiveData<Resource<TransportsViewModel>>()

    fun onInit(place: TransportsViewModel?){
        place?.let {
            placeData.value = Resource.success(place)
        }?: run {
            placeData.value = Resource.error()
        }
    }
}