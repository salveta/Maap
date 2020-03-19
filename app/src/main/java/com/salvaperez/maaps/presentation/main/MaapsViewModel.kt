package com.salvaperez.maaps.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.salvaperez.maaps.domain.model.TransportsModel
import com.salvaperez.maaps.domain.use_case.GetTransportUseCase
import com.salvaperez.maaps.presentation.util.Event
import com.salvaperez.maaps.presentation.util.Resource
import com.salvaperez.maaps.presentation.util.ScopedViewModel
import com.salvaperez.maaps.presentation.view_model.TransportsViewModel
import com.salvaperez.maaps.presentation.view_model.toViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MaapsViewModel(private val getTransportUseCase: GetTransportUseCase, uiDispatcher: CoroutineDispatcher): ScopedViewModel(uiDispatcher) {

    private var transportListCache: List<TransportsViewModel> = emptyList()

    private val _moveCamera: MutableLiveData<Event<LatLng>> by lazy { MutableLiveData<Event<LatLng>>() }
    val moveCamera : LiveData<Event<LatLng>>
        get() = _moveCamera

    private val _onClickInfoWindow: MutableLiveData<Event<TransportsViewModel>> by lazy { MutableLiveData<Event<TransportsViewModel>>() }
    val onClickInfoWindow : LiveData<Event<TransportsViewModel>>
        get() = _onClickInfoWindow

    private val _transportPlaces: MutableLiveData<Resource<TransportsViewModel>> by lazy { MutableLiveData<Resource<TransportsViewModel>>() }
    val transportPlaces : LiveData<Resource<TransportsViewModel>>
        get() = _transportPlaces


    fun onInit(){
        initScope()
        _transportPlaces.value = Resource.loading(true)

        launch {
            getTransportUseCase(
                onGetErrorTransport = {hideLoading()},
                onGetTransportSuccess = {getTransportList(it)}
            )
        }
    }

    fun getTransportList(transportList: List<TransportsModel>?){
        if(!transportList.isNullOrEmpty()) {
            this.transportListCache = transportList.map { it.toViewModel() }
            _moveCamera.value = Event(transportList.first().toViewModel().latLong)

            for(transport in transportList){
                _transportPlaces.value = Resource.success(transport.toViewModel())
            }
            hideLoading()
        }else{
            _transportPlaces.value = Resource.error()
            hideLoading()
        }
    }

    private fun hideLoading(){
        _transportPlaces.value = Resource.loading(false)
    }

    fun clickOnInfoWindow(latLng: LatLng){
        for (place in transportListCache) {
            if (latLng == place.latLong) {
                _onClickInfoWindow.value = Event((place))
            }
        }
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}