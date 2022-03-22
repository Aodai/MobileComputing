package com.odai.mobilecomputing

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class HomeFragment : Fragment(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var mainActivity: MainActivity
    private lateinit var tvSensorValue: TextView
    private var accelerometer: Sensor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = requireActivity() as MainActivity
        sensorManager = mainActivity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        // Inflate the layout for this fragment
        var view: View? = inflater.inflate(R.layout.fragment_home, container, false)
        if (view != null) {
            tvSensorValue = view.findViewById(R.id.tvSensorValue)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        accelerometer.also { accel ->
            sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val sides = event.values[0]
            val upDown = event.values[1]
            if(upDown.toInt() == 0 && sides.toInt() == 0)
                tvSensorValue.text = getString(R.string.flat)
            else
                tvSensorValue.text = getString(R.string.tilted)
        }
    }

    override fun onAccuracyChanged(event: Sensor?, accuracy: Int) {
        return
    }

}