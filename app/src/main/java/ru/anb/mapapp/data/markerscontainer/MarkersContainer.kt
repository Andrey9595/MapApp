package ru.anb.mapapp.data.markerscontainer

import ru.anb.mapapp.data.Geometry
import ru.anb.mapapp.data.Marker
import ru.anb.mapapp.data.Properties

class MarkersContainer {
    private val markersList = listOf(
        Marker(
            Geometry(listOf(55.755811, 37.617617), "Point"),
            Properties("http://docs.evostream.com/sample_content/assets/hls-bunny-166/playlist.m3u8"),
            "Feature"
        ),
        Marker(
            Geometry(listOf(55.655811, 37.417617), "Point"),
            Properties("http://docs.evostream.com/sample_content/assets/hls-bunny-166/playlist.m3u8"),
            "Feature"
        ),
        Marker(
            Geometry(listOf(55.555811, 37.517617), "Point"),
            Properties("http://docs.evostream.com/sample_content/assets/hls-bunny-166/playlist.m3u8"),
            "Feature"
        )
    )

    fun getMarkers() = markersList

}