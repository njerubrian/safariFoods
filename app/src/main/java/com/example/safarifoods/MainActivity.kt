package com.example.safarifoods

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class  MainActivity : AppCompatActivity() {
    lateinit var Highwayspinner:Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Highwayspinner = findViewById(R.id.spinner)
        val highway = resources.getStringArray(R.array.Routes)

        //specify the list to be shown by our sppinner
       if (Highwayspinner!=null) {
           val adapter= ArrayAdapter(this, android.R.layout.simple_spinner_item, highway)
           Highwayspinner.adapter = adapter
           Highwayspinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
               override fun onItemSelected(
                   parent: AdapterView<*>?,
                   view: View?,
                   position: Int,
                   id: Long
               ) {
                   Toast.makeText(this@MainActivity, parent?.selectedItemPosition.toString(), Toast.LENGTH_LONG).show()

                   if (parent?.selectedItemPosition!!.equals(1)) {
                       Toast.makeText(
                           applicationContext,
                           "you have selected Nairobi-Meru Highway",
                           Toast.LENGTH_LONG
                       ).show()


                       val transction = supportFragmentManager.beginTransaction()
                       transction.add(R.id.mycontainer, Naimeru())
                       transction.disallowAddToBackStack()
                       transction.commit()

                   } else if (parent?.selectedItemPosition!!.equals(2)) {
                       Toast.makeText(
                           applicationContext,
                           "you have selected Nairobi-Eldoret Highway",
                           Toast.LENGTH_LONG
                       ).show()
                       val transction = supportFragmentManager.beginTransaction()
                       transction.add(R.id.mycontainer, NaiEldy())
                       transction.disallowAddToBackStack()
                       transction.commit()
                   } else if (parent?.selectedItemPosition!!.equals(3)) {
                       Toast.makeText(
                           applicationContext,
                           "you have selected Nairobi-Mombasa Highway",
                           Toast.LENGTH_LONG).show()
                       val transction = supportFragmentManager.beginTransaction()
                       transction.add(R.id.mycontainer,NaiMombasa())
                     transction.disallowAddToBackStack()
                      transction.commit()

                   }
               }

               override fun onNothingSelected(parent: AdapterView<*>?) {
                   Toast.makeText(applicationContext,"Please selecet a route you are using",Toast.LENGTH_LONG).show()
               }

           }
       }

        //instance of the restaurants lists using the data model class (Resmodel)
        val RestaurantList:ArrayList<ResModel> = ArrayList()


        // lets claa the JSon Object from the assests folder
        try {
            val obj = JSONObject(getJSONFromAssets()!!)
            // fetch the restaurants Json Array
            val ResArray = obj.getJSONArray("restuarants")
            //get the restaurant information using for loop

            for (i in 0 until ResArray.length()) {
                //create the JsonObject for fetching restaurant data
               val restaurant= ResArray.getJSONObject(i)
                val rest_name = restaurant.getString("restaurant_name")
                val rest_id = restaurant.getInt("restaurant_id")
                val rest_hours = restaurant.getString("hours")
                val  rest_phone= restaurant.getString("restaurant_phone")
                //create an object for getting the city and the street
                val rest_adress = restaurant.getJSONObject("address")

                val rest_city = rest_adress.getString("city")
                val rest_street = rest_adress.getString("street")

                //add the captured data from our json to the model class

                val Rest_Details = ResModel(rest_name,rest_id,rest_phone,rest_hours,rest_city,rest_street)
                  // add the details to the list
                RestaurantList.add(Rest_Details)

            }
        }
        catch (e: JSONException){
            e.printStackTrace()

        }
        // set the layout for the recyclerView.
  myrecycler.layoutManager = LinearLayoutManager(this)
        myrecycler.setHasFixedSize(true)
       val itemAdapter = ResAdapter(this,RestaurantList)
        //set the adapter instance to the recyclerview to inflate the items.

        myrecycler.adapter = itemAdapter


    }

    private fun getJSONFromAssets(): String? {
        var json:String? = null
        val Charsets: Charset = Charsets.UTF_8
        try {
            val aset = assets.open("restaurant.json")
            val size = aset.available()
            val buffer = ByteArray(size)
            aset.read(buffer)
            aset.close()
            json = String(buffer, Charsets)

        }
        catch (ex: IOException){
            ex.printStackTrace()
            return  null

        }
return json
    }



}