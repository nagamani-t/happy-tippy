package com.example.happy
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
class happy_home_Adapter (var homeList:List<UsersItem>,var con :Context):RecyclerView.Adapter<happy_home_Adapter.home_items_ViewHolder>(){
    inner class home_items_ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    var body:TextView = itemView.findViewById(R.id.home_body)
        var title :TextView = itemView.findViewById(R.id.home_title)
    }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): home_items_ViewHolder {
         var view =LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
         return home_items_ViewHolder(view)
     }

     override fun getItemCount(): Int {
       return homeList.size
     }

     override fun onBindViewHolder(holder: home_items_ViewHolder, position: Int) {

holder.title.text= homeList[position].title
         holder.body.text = homeList[position].body
     }
 }