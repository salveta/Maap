package com.salvaperez.maaps.domain.model

import com.salvaperez.maaps.data.entity.TransportsEntity

fun TransportsEntity.toModel(): TransportsModel{
    return TransportsModel(
        allowDropoff = allowDropoff,
        availableResources = availableResources,
        batteryLevel = batteryLevel,
        bikesAvailable = bikesAvailable,
        companyZoneId = companyZoneId,
        engineType = engineType,
        helmets = helmets,
        id = id,
        lat = lat,
        licencePlate = licencePlate,
        locationType = locationType,
        lon = lon,
        model = model,
        name = name,
        pricePerMinuteDriving = pricePerMinuteDriving,
        pricePerMinuteParking = pricePerMinuteParking,
        range = range,
        realTimeData = realTimeData,
        resourceImageId = resourceImageId,
        resourceType = resourceType,
        scheduledArrival = scheduledArrival,
        seats = seats,
        spacesAvailable = spacesAvailable,
        station = station,
        latitud = latitud,
        longitud = longitud
    )
}