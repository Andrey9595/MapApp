package ru.anb.mapapp.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.JsonObject
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import ru.anb.mapapp.Constants
import ru.anb.mapapp.R
import ru.anb.mapapp.data.markerscontainer.MarkersContainer


class MapFragment : Fragment(), OnMapReadyCallback {

    var mapView: MapView? = null
    private lateinit var map: MapboxMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Mapbox.getInstance(requireContext(), getString(R.string.accessToken))
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = requireActivity().findViewById(R.id.mapView)
        mapView?.getMapAsync(this)
    }

    override fun onMapReady(mapboxMap: MapboxMap) {
        this.map = mapboxMap
        mapboxMap.setStyle(
            Style.Builder().fromUri("mapbox://styles/mapbox/streets-v11")
        ) { style ->
            map.locationComponent.activateLocationComponent(
                LocationComponentActivationOptions.Builder(
                    requireContext(),
                    style
                ).build()
            )
// установка начальой позиции на карте
            map.moveCamera { CameraPosition.Builder().target(LatLng(55.755811, 37.617617)).build() }
// создание меток на карте
            val symbolManager = SymbolManager(mapView!!, mapboxMap, style)
            symbolManager.iconAllowOverlap = true
            style.addImage("myMarker", BitmapFactory.decodeResource(resources, R.drawable.marker))
            MarkersContainer().getMarkers().forEach { marker ->
                symbolManager.create(
                    SymbolOptions()
                        .withLatLng(
                            LatLng(
                                marker.geometry.coordinates.first(),
                                marker.geometry.coordinates.last()
                            )
                        )
                        .withIconImage("myMarker")
                        .withData(JsonObject().also {
                            it.addProperty("VideoUrl", marker.properties.video_url)
                        })
                )
            }

            symbolManager.addClickListener { symbol ->
                Log.d("stroka", symbol.data.toString())
// передаем урл видео через бандл
                val bundle = Bundle().also {
                    it.putString(
                        Constants.VIDEO_URL_KEY,
                        "https://docs.evostream.com/sample_content/assets/hls-bunny-166/playlist.m3u8"
                    )
                }

                val videoDialog = VideoDialog().also {
                    it.arguments = bundle
                }

                videoDialog.show(parentFragmentManager, "video_dialog")
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

}