package ru.anb.mapapp.data

data class Marker(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)