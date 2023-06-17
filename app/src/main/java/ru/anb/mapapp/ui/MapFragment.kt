package ru.anb.mapapp.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import ru.anb.mapapp.R


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
        ) {
            map.locationComponent.activateLocationComponent(
                LocationComponentActivationOptions.Builder(
                    requireContext(),
                    map.style!!
                ).build()
            )

            map.moveCamera { CameraPosition.Builder().target(LatLng(55.755811, 37.617617)).build() }

            val symbolManager = SymbolManager(mapView!!, mapboxMap, it)
            symbolManager.iconAllowOverlap = true
            it.addImage("myMarker", BitmapFactory.decodeResource(resources, R.drawable.marcer))
            symbolManager.create(
                SymbolOptions()
                    .withLatLng(LatLng(55.755811, 37.617617))
                    .withIconImage("myMarker")
                    .withData(JsonObject().also {
                        it.addProperty("Test", "testData")
                    })
            )

            symbolManager.create(
                SymbolOptions()
                    .withLatLng(LatLng(55.855811, 37.717617))
                    .withIconImage("myMarker")
                    .withData(JsonObject().also {
                        it.addProperty("video", "http://docs.evostream.com/sample_content/assets/hls-bunny-166/playlist.m3u8")
                    })
            )

            symbolManager.addClickListener(){
                val videoDialog = VideoDialog()
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