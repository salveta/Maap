package com.salvaperez.maaps.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.salvaperez.maaps.R
import com.salvaperez.maaps.presentation.commons.gone
import com.salvaperez.maaps.presentation.commons.open
import com.salvaperez.maaps.presentation.commons.showToast
import com.salvaperez.maaps.presentation.commons.visible
import com.salvaperez.maaps.presentation.detail.DetailActivity
import com.salvaperez.maaps.presentation.util.EventObserver
import com.salvaperez.maaps.presentation.util.Resource
import com.salvaperez.maaps.presentation.view_model.TransportsViewModel
import kotlinx.android.synthetic.main.activity_maps.*
import org.koin.android.ext.android.inject
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val vModel: MaapsViewModel by currentScope.viewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        initMap()
        loadObservers()
        vModel.onInit()
    }

    private fun initMap(){
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun loadObservers(){
        vModel.transportPlaces.observe(this, Observer { transports ->
            when(transports.status){
                Resource.Status.SUCCESS -> showTransports(transports.data as TransportsViewModel)
                Resource.Status.ERROR -> showError()
                Resource.Status.LOADING -> showLoading(transports.data as Boolean)
            }
        })

        vModel.moveCamera.observe(this, EventObserver { latLn ->
            moveCameraTo(latLn)
        })

        vModel.onClickInfoWindow.observe(this, EventObserver{ transportViewModel ->
            openDetail(transportViewModel)
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnInfoWindowClickListener(OnInfoWindowClickListener { marker ->
            vModel.clickOnInfoWindow(marker.position)
        })
    }

    private fun showTransports(transports: TransportsViewModel) {
        mMap.addMarker(MarkerOptions()
            .position(transports.latLong)
            .title(transports.name)
            .icon(BitmapDescriptorFactory.defaultMarker(transports.icon)))
    }

    private fun moveCameraTo(position: LatLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(position))
        mMap.animateCamera(CameraUpdateFactory.zoomIn())
        mMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_TO),
            MILLIS, null)
    }

    private fun openDetail(place: TransportsViewModel) {
        val extras = Bundle()
        extras.putParcelable(DetailActivity.PLACE, place)
        open(DetailActivity::class.java, extras)
    }

    private fun showError() {
        showToast(getString(R.string.error_retrieve_transports))
    }

    private fun showLoading(boolean: Boolean) {
        if(boolean) progressLoading.visible()
        else progressLoading.gone()
    }

    companion object{
        const val ZOOM_TO = 17.0f
        const val MILLIS = 2000
    }
}
