package com.example.safarifoods

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.safarifoods.MenuAdapter.MenuViewHolder
import kotlinx.android.synthetic.main.menu_items.view.*

class MenuAdapter(private val context:Context,
                  private val MenuList: List<MenuModel>
                  ):
    RecyclerView.Adapter<MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            MenuViewHolder {
        val inflater =LayoutInflater.from(parent.context).inflate(R.layout.restaurant_items,parent,false)
        return MenuViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val items = MenuList[position]
//
        holder.menu_name.text = "Menu Name: " +items.menuName
        holder.section_name.text = "Section Name: " +items.SectionName
        holder.section_description.text = "Description: " +items.descrption
        holder.food_name.text = "Food Name: " + items.foodName
        holder.food_description.text = "food description: " + items.foodDescrption
        holder.food_price.text = "fixed price: " + items.foodPrice
        holder.grid_items.setOnClickListener {

        }



    }



    override fun getItemCount(): Int {
        return MenuList.size
    }
     class MenuViewHolder(view: View):RecyclerView.ViewHolder(view){
        val menu_name = view.menuName
        val section_name = view.sectionName
        val section_description = view.MenuDescription
       val grid_items = view.mygrid
        val food_name = view.foodName
        val food_description = view.foodDescription
        val food_price = view.price

//        init {
//            itemView.setOnClickListener(this)
//        }

//        override fun onClick(v: View?) {
//
//            val position:Int = adapterPosition
//            if (position!= RecyclerView.NO_POSITION)
//            listener.onItemClick(position)
//        }


    }
//  public  interface OnItemClickListener{
//        fun onItemClick(position: Int)
//    }
}