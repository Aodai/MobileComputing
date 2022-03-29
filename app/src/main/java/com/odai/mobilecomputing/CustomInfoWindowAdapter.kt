package com.odai.mobilecomputing

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindowAdapter(mContext: Context) : GoogleMap.InfoWindowAdapter {

    var mWindow: View = LayoutInflater.from(mContext).inflate(R.layout.custom_info_window, null)

    private fun renderWindow(marker: Marker, view: View) {
        var title = marker.title
        var tvTitle: TextView = view.findViewById(R.id.tvTitle)
        tvTitle.text = title
        var snippet = marker.snippet
        var tvSnippet: TextView = view.findViewById(R.id.tvSnippet)
        tvSnippet.text = snippet
        var ivMarkerImage: ImageView = view.findViewById(R.id.ivMarkerImage)
        ivMarkerImage.setBackgroundResource(R.drawable.park)
    }

    override fun getInfoWindow(marker: Marker): View? {
        renderWindow(marker, mWindow)
        return mWindow
    }

    override fun getInfoContents(marker: Marker): View? {
        renderWindow(marker, mWindow)
        return mWindow
    }

}