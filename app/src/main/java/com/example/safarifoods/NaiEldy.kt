package com.example.safarifoods

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_nai_eldy.*
import kotlinx.android.synthetic.main.menu_items.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

 class NaiEldy : Fragment(){
 lateinit var naieldo:RecyclerView
   //abstract val listener: MenuAdapter.OnItemClickListener


    // create an instance of the model class
    val MenuList:ArrayList<MenuModel> = ArrayList()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_nai_eldy, container, false)
        naieldo = view.findViewById(R.id.eldyrecycler)

        //create an object to  get the data from our Json In the Assets Array
        //lets use the try and catch methods.

        try {
            // create an object of the json array
            val obj = JSONObject(getJSONFromAssets()!!)

            // fetch the restaurants Json Array
            val menuArray = obj.getJSONArray("restuarants")
            //get the restaurant information using for loop

            for (i in 0 until menuArray.length()){
                val menuThing = menuArray.getJSONObject(i)
                val menuS=  menuThing.getJSONArray("menus")
                for (e in 0 until menuS.length()){
                    val itemMenu = menuS.getJSONObject(e)
                    val menuname = itemMenu.getString("menu_name")
                    val menu_section = itemMenu.getJSONArray("menu_sections")
                    Log.d("menu sectionThings","items" +menuname +menu_section)
                    for (k in 0 until menu_section.length()){
                        val menuSections = menu_section.getJSONObject(k)
                    val section_name = menuSections.getString("section_name")
                    val section_descrption = menuSections.getString("description")

                    val menuItems = menuSections.getJSONArray("menu_items")

                    Log.d("menusSections","menu details" +section_name +section_descrption)
                    for (j in 0 until menuItems.length()){
                        val menu_items = menuItems.getJSONObject(j)
                        val food_name =menu_items.getString("name")
                        val food_description = menu_items.getString("description")
                    val food_price = menu_items.getInt("price")
                        Log.d("menu items","menu items are" +food_name +food_description +food_price)

                        val menuFetchedItems = MenuModel(menuname,section_name,section_descrption,food_name,food_description,food_price)
                        MenuList.add(menuFetchedItems)

                        // set the layout for the recyclerView.
                        naieldo.layoutManager = LinearLayoutManager(activity)
                        naieldo.setHasFixedSize(true)
//            val itemAdapter = MenuAdapter(activity!!,MenuList)
                        //set the adapter instance to the recyclerview to inflate the items.

                        naieldo.adapter = MenuAdapter(requireActivity(),MenuList)

                    }
                    }

                }


            }





        }
        catch (e:JSONException){
            e.printStackTrace()

        }

        return view
        //setOnclickListener()

    }

//    private fun setOnclickListener() {
//        activity?.let {
//            val menuorderIntent = Intent(activity, OrderItems::class.java)
//            menuorderIntent.putExtra("Food Name", MenuList)
//            menuorderIntent.putExtra("food description", MenuList)
//            menuorderIntent.putExtra("fixed price", MenuList)
//            it.startActivity(menuorderIntent)
//        }




//    override fun onItemClick(position: Int) {
//        Toast.makeText(activity, "item clicked" +position, Toast.LENGTH_SHORT).show()
//        val clickedItem = MenuList[position]






private fun getJSONFromAssets(): String? {
        var json:String? = null
        val Charsets: Charset = Charsets.UTF_8
        try {
            val aset = context?.assets?.open("restaurant.json")
            val size = aset?.available()
            val buffer = ByteArray(size!!)
            aset?.read(buffer)
            aset?.close()
            json = String(buffer, Charsets)

        }
        catch (ex: IOException){
            ex.printStackTrace()
            return  null

        }
        return json
    }



}