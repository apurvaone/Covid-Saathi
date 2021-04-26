package com.example.covidsaathi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
//AIzaSyAFetOVp1JvGBBp2QnJHF8GKi0lXSnQBZw
class MainActivity : AppCompatActivity() {
    lateinit var progressBar: ProgressBar
    private lateinit var  mAdapter:StateAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager= LinearLayoutManager(this)
        progressBar= findViewById(R.id.progress_bar)
        fetchData()
        mAdapter= StateAdapter()

        recycler_view.adapter=mAdapter


     /*   getSupportActionBar()!!.setDisplayShowHomeEnabled(true)
        getSupportActionBar()!!.setLogo(R.drawable.logo24)
        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)

        getSupportActionBar()!!.setDisplayUseLogoEnabled(true)
*/

        this.getSupportActionBar()?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar()?.setDisplayShowCustomEnabled(true);
       supportActionBar!!.setCustomView(R.layout.custom_actionbar)

    }

    private fun fetchData() {


progressBar.visibility=View.VISIBLE
        val url="https://api.covid19india.org/data.json"

        val dts:String="31/12/1998"
        val dt1:Date= SimpleDateFormat("dd/MM/yyyy").parse(dts)




        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener
        {

            val stateJsonArray = it.getJSONArray("statewise")
            val stateArray = ArrayList<State>()

            for (i in 0 until stateJsonArray.length()) {
                val stateJsonObject = stateJsonArray.getJSONObject(i)
                val state = State(
                    stateJsonObject.getString("state"),
                    stateJsonObject.getString("confirmed"),
                    stateJsonObject.getString("active"),
                    stateJsonObject.getString("deaths"),
                    stateJsonObject.getString("recovered"),
                   stringTODate(stateJsonObject.getString("lastupdatedtime").toString() ).getTimeAgo()



                )

                stateArray.add(state)
            }

            mAdapter.updateNews(stateArray)


           val handler=Handler()
            handler.postDelayed({delay()},2000)



        },
            {
                progressBar.visibility=View.GONE
            })

        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)



    }

    fun delay(){
        progressBar.visibility=View.GONE
    }

    fun Date.getTimeAgo(): String {
        val calendar = Calendar.getInstance()
        calendar.time = this

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val currentCalendar = Calendar.getInstance()

        val currentYear = currentCalendar.get(Calendar.YEAR)
        val currentMonth = currentCalendar.get(Calendar.MONTH)
        val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = currentCalendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentCalendar.get(Calendar.MINUTE)

        return if (year < currentYear ) {
            val interval = currentYear - year
            if (interval == 1) "$interval year ago" else "$interval years ago"
        } else if (month < currentMonth) {
            val interval = currentMonth - month
            if (interval == 1) "$interval month ago" else "$interval months ago"
        } else  if (day < currentDay) {
            val interval = currentDay - day
            if (interval == 1) "$interval day ago" else "$interval days ago"
        } else if (hour < currentHour) {
            val interval = currentHour - hour
            if (interval == 1) "$interval hour ago" else "$interval hours ago"
        } else if (minute < currentMinute) {
            val interval = currentMinute - minute
            if (interval == 1) "$interval minute ago" else "$interval minutes ago"
        } else {
            "a moment ago"
        }
    }

    fun stringTODate(dts:String):Date
    {

        val dt1:Date= SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dts)
        return dt1
    }


}