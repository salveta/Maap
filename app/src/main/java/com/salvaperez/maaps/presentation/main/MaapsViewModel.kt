package com.salvaperez.maaps.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.salvaperez.maaps.domain.model.TransportsModel
import com.salvaperez.maaps.domain.use_case.GetTransportUseCase
import com.salvaperez.maaps.presentation.util.Event
import com.salvaperez.maaps.presentation.util.Resource
import com.salvaperez.maaps.presentation.view_model.TransportsViewModel
import com.salvaperez.maaps.presentation.view_model.toViewModel

class MaapsViewModel(private val getTransportUseCase: GetTransportUseCase): ViewModel() {

    var transportListCache: List<TransportsViewModel> = emptyList()
    val transportPlaces = MutableLiveData<Resource<TransportsViewModel>>()
    val moveCamera = MutableLiveData<Event<LatLng>>()
    val onClickInfoWindow = MutableLiveData<Event<TransportsViewModel>>()

    fun onInit(){
        transportPlaces.value = Resource.loading(true)
        getTransportUseCase(
            onGetErrorTransport = {hideLoading()},
            onGetTransportSuccess = {getTransportList(it)}
        )
    }

    fun getTransportList(transportList: List<TransportsModel>?){
        if(!transportList.isNullOrEmpty()) {
            this.transportListCache = transportList.map { it.toViewModel() }
            moveCamera.value = Event(transportList.first().toViewModel().latLong)

            for(transport in transportList){
                transportPlaces.value = Resource.success(transport.toViewModel())
            }
            hideLoading()
        }else{
            transportPlaces.value = Resource.error()
            hideLoading()
        }
    }

    private fun hideLoading(){
        transportPlaces.value = Resource.loading(false)
    }

    fun clickOnInfoWindow(latLng: LatLng){
        for (place in transportListCache) {
            if (latLng == place.latLong) {
                onClickInfoWindow.value = Event((place))
            }
        }
    }
}