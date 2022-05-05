package com.odai.mobilecomputing

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://quotes.rest/"

class QuoteFragment : Fragment() {
    private lateinit var tvQuote: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var snack: Snackbar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View? = inflater.inflate(R.layout.fragment_quote, container, false)
        if(view != null) {
            tvQuote = view.findViewById(R.id.tvQuote)
            tvAuthor = view.findViewById(R.id.tvAuthor)
        }

        if(checkInternet()) {
            getQuote()
        }
        else {

            snack = Snackbar.make(requireActivity().findViewById(R.id.drawerLayout),"Can't connect to internet", Snackbar.LENGTH_LONG)
            snack.setAction("Turn on WiFi", View.OnClickListener {
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            })
            snack.show()
            tvQuote.text = "Could not connect to internet!"
        }
        return view
    }

    @SuppressLint("MissingPermission")
    private fun checkInternet(): Boolean {
        val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    private fun getQuote() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(QuotesApiInterface::class.java)

        val retrofitData = retrofitBuilder.getQuote()

        retrofitData.enqueue(object : Callback<Quote?> {
            override fun onResponse(call: Call<Quote?>, response: Response<Quote?>) {
                val respBody = response.body()!!
                val quote = respBody.contents.quotes[0].quote
                val author = respBody.contents.quotes[0].author

                tvQuote.text = quote
                tvAuthor.text = author
            }

            override fun onFailure(call: Call<Quote?>, t: Throwable) {
                tvQuote.text = "An error occurred while trying to fetch the quote of the day."
            }
        })
    }
}