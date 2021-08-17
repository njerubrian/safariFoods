package com.example.safarifoods

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.restaurant_items.view.*


class ResAdapter(private val context:Context, private val RestuarantList: List<ResModel>):
    RecyclerView.Adapter<ResAdapter.RestuarantViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ResAdapter.RestuarantViewHolder {
        val inflater =LayoutInflater.from(parent.context).inflate(R.layout.restaurant_items,parent,false)
        return ResAdapter.RestuarantViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RestuarantViewHolder, position: Int) {
        val items = RestuarantList[position]
        //bind the dtata
        holder.res_name.text = items.Res_name
        holder.res_id.text = "Restaurant ID: " +items.Res_id
        holder.res_phone.text = "Restaurant PhoneNumber: " +items.Res_phone
        holder.res_hours.text = "Restaurant open Hours: " +items.Res_Hours
        holder.res_city.text ="Located in: " +items.Res_city + " city"
        holder.res_street.text = "Restaurant located along" +items.Res_streets

    }

    override fun getItemCount(): Int {
        return RestuarantList.size
    }

    class RestuarantViewHolder(view:View):RecyclerView.ViewHolder(view) {
      val res_name = view.res_name
        val res_id = view.res_id
        val res_phone = view.res_phone
        val res_hours =view.hours
        val res_city = view.city
        val res_street = view.street

    }
}