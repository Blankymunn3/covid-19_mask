package io.kim_kong.covid_19_mask.view

import android.graphics.Color
import android.graphics.PointF
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import io.kim_kong.covid_19_mask.R
import io.kim_kong.covid_19_mask.api.APIClient
import io.kim_kong.covid_19_mask.api.CovidAPI
import io.kim_kong.covid_19_mask.api.CovidResponse
import io.kim_kong.covid_19_mask.databinding.ActivityMainBinding
import io.kim_kong.covid_19_mask.model.Store
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var locationSource: FusedLocationSource
    lateinit var binding: ActivityMainBinding

    lateinit var covidAPI: CovidAPI

    lateinit var responseStore: List<Store>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)
        binding.activity = this@MainActivity
        covidAPI = APIClient.instance.create(CovidAPI::class.java)

        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        val uiSettings = naverMap.uiSettings
        naverMap.isIndoorEnabled = true
        uiSettings.isCompassEnabled = true
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        

        naverMap.setOnMapClickListener { _, latLng ->
            covidAPI.getLocation(latLng.latitude, latLng.longitude, 10000).enqueue(object :
                Callback<CovidResponse> {
                override fun onFailure(call: Call<CovidResponse>, t: Throwable) {
                    t.printStackTrace()
                }

                override fun onResponse(call: Call<CovidResponse>, response: Response<CovidResponse>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            responseStore = response.body()!!.stores!!
                            for (i in response.body()!!.stores!!.indices) {
                                var storeName = responseStore[i].name.split("test::")

                                if (responseStore[i].sold_out) {
                                    val infoWindow = InfoWindow()
                                    infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this@MainActivity) {
                                        override fun getText(infoWindow: InfoWindow): CharSequence {
                                            return "${storeName[1]}\nSold Out"
                                        }
                                    }
                                    infoWindow.position = LatLng(responseStore[i].lat.toDouble(), responseStore[i].lng.toDouble())
                                    infoWindow.open(naverMap)
                                } else {
                                    val infoWindow = InfoWindow()
                                    infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this@MainActivity) {
                                        override fun getText(infoWindow: InfoWindow): CharSequence {
                                            return "${storeName[1]}\n${responseStore[i].remain_cnt}"
                                        }
                                    }
                                    infoWindow.position = LatLng(responseStore[i].lat.toDouble(), responseStore[i].lng.toDouble())
                                    infoWindow.open(naverMap)
                                }
                            }
                        }
                    }
                }
            })
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}
