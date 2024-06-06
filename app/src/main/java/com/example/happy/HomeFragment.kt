package com.example.happy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {
    var BASE_URL = "https://jsonplaceholder.typicode.com"

    //private var homeList = ArrayList<usersItem>()
    private lateinit var adapter: happy_home_Adapter
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        arguments?.let {}
    }
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Find the RecyclerView from your layout
        recyclerView = view.findViewById(R.id.happy_home_list)// Replace with your actual id
        recyclerView.layoutManager = LinearLayoutManager(context)
        // Prepare your dummy data (replace with your data source)
        addDataToList()
        return view
    }

    private fun addDataToList() {
        var retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build().create(Api_Interface::class.java)
        var retroData = retrofit.getData()
        Log.i("retroDataaaaa",retroData.toString())
        retroData.enqueue(object : Callback<List<UsersItem>> {
            override fun onResponse(
                call: Call<List<UsersItem>>, response: Response<List<UsersItem>>
            ) {
                var homeList = response.body() !!
                Log.i("homelisttt",homeList.toString())
                adapter = happy_home_Adapter(homeList, requireContext())
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<List<UsersItem>>, t: Throwable) {
                Log.d("onFailure", "api is closing because of error")
            }

        })
    }
}

