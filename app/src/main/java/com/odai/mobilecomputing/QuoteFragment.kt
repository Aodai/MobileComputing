package com.odai.mobilecomputing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        getQuote()
        return view
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