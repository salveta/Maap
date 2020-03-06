package com.salvaperez.maaps.data.entity

import com.google.gson.annotations.SerializedName

data class TransportsEntity(
    @SerializedName("allowDropoff") val allowDropoff: Boolean?,
    @SerializedName("availableResources") val availableResources: Int?,
    @SerializedName("batteryLevel") val batteryLevel: Int,
    @SerializedName("bikesAvailable") val bikesAvailable: Int?,
    @SerializedName("companyZoneId") val companyZoneId: Int,
    @SerializedName("engineType") val engineType: String?,
    @SerializedName("helmets") val helmets: Int,
    @SerializedName("id") val id: String,
    @SerializedName("lat") val lat: Double?,
    @SerializedName("licencePlate") val licencePlate: String?,
    @SerializedName("locationType") val locationType: Int?,
    @SerializedName("lon") val lon: Double?,
    @SerializedName("model") val model: String?,
    @SerializedName("name") val name: String,
    @SerializedName("pricePerMinuteDriving") val pricePerMinuteDriving: Double?,
    @SerializedName("pricePerMinuteParking") val pricePerMinuteParking: Double?,
    @SerializedName("range") val range: Int,
    @SerializedName("realTimeData") val realTimeData: Boolean,
    @SerializedName("resourceImageId") val resourceImageId: String?,
    @SerializedName("resourceType") val resourceType: String?,
    @SerializedName("scheduledArrival") val scheduledArrival: Int?,
    @SerializedName("seats") val seats: Int?,
    @SerializedName("spacesAvailable") val spacesAvailable: Int?,
    @SerializedName("station") val station: Boolean?,
    @SerializedName("x") val longitud: Double,
    @SerializedName("y") val latitud: Double
)