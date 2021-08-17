package com.example.safarifoods

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_order_items.*

class OrderItems : AppCompatActivity() {
    lateinit var foodname: TextView

    var f_name: String = "food not selected"
    var f_descrip: String = " "
    var f_price: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_items)

        //pass the fetched information to the database
    }

    fun save_fetchedItems(view: View) {
        val extras = intent.extras
        f_name = extras?.getString("Food Name")!!
        f_descrip = extras?.getString("food description")!!
        f_price = extras?.getString("fixed price")!!

        fetchedname.text = f_name
        fetcheddescrpition.text = f_descrip
        fetchedprice.text = f_price.toString()
       val quantity = orderQuabtity.text.toString()


        Log.d("fetched data", "details" + f_name + f_descrip + f_price +quantity)
        val databaseHelper = MenuDatabase(this)

        if (f_name.trim() != "" && f_descrip.trim() != "" && f_price.trim()!="") {
      val Order_status = databaseHelper.getMenuItems(sqliteModel(f_name,f_descrip,f_price,quantity))
      if (Order_status>-1){
          Toast.makeText(this,"orders recorded succesfully",Toast.LENGTH_LONG).show()

      }

        }
        else{
            Toast.makeText(this,"click on the food items to make an order",Toast.LENGTH_LONG).show()
        }
    }
}