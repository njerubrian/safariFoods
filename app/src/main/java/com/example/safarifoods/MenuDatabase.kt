package com.example.safarifoods

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MenuDatabase(context: Context):
    SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "Menu_Database"
        private val TABLE_MENU = "menu_table"
        private val KEY_ID = "id"
        private val KEY_FOODNAME = "food_name"
        private val KEY_FOODDESCRIPTION ="food_description"
        private  val KEY_PRICE = "food_price"
        private val KEY_Quantity = "item_Quantity"
    }
    //cerate the sqlite database.
    override fun onCreate(db: SQLiteDatabase?) {
        // define the create query.
        val CREATE_MENU_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_MENU +
                "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FOODNAME + " TEXT," + KEY_FOODDESCRIPTION + " TEXT," + KEY_PRICE +"INTEGER," + KEY_Quantity + "INTEGER" + ")")
        // EXECUTE THE QUERY
        db?.execSQL(CREATE_MENU_TABLE)
        Log.d("db response","db creation error is" +db?.execSQL(CREATE_MENU_TABLE))
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_MENU)
        onCreate(db)
    }
    // saving the food items fetched from json into thedatabase.
    fun getMenuItems(sqliteModel: sqliteModel):Long{
        val db =this.writableDatabase
        // define and place our values into the menu table.
        val contentvalue = ContentValues()
        contentvalue.put(KEY_FOODNAME,sqliteModel.fetched_food)
        contentvalue.put(KEY_FOODDESCRIPTION,sqliteModel.fetched_description)
        contentvalue.put(KEY_PRICE,sqliteModel.fetched_price)
        contentvalue.put(KEY_Quantity,sqliteModel.order_Quantity)


        //query to insert into the database.
        val success = db.insert(TABLE_MENU,null,contentvalue)

        //return our input to the datbase
        return success

    }

    fun read_orders():List<sqliteModel>{
        val order_array:ArrayList<sqliteModel> = ArrayList<sqliteModel>()

        val selectQuery= "SELECT * FROM $TABLE_MENU"
        val  db = this.readableDatabase
        var cursor:Cursor? =null

        try {
            cursor = db.rawQuery(selectQuery,null)

        }
        catch (e:SQLException){
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var foodNames:String
        var foodDescrptions:String
        var foodPrices:String
        var orderQuantity:String

        if (cursor.moveToFirst()){
            do {
                foodNames =cursor.getString(cursor.getColumnIndex("food_name"))
                foodDescrptions =cursor.getString(cursor.getColumnIndex("food_description"))
                foodPrices = cursor.getString(cursor.getColumnIndex("food_price"))
                orderQuantity = cursor.getString(cursor.getColumnIndex("item_Quantity"))

                val orders =sqliteModel(
                    fetched_food = foodNames, fetched_description = foodDescrptions,
                fetched_price = foodPrices, order_Quantity = orderQuantity)
                order_array.add(orders)

            }
                while (cursor.moveToNext())
        }
        return order_array
    }

}