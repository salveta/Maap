package com.salvaperez.maaps.presentation.view_model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransportsViewModel(
    val id: String,
    val name: String,
    val latLong: LatLng,
    val icon: Float
): Parcelable