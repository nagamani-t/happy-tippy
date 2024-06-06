package com.example.happy
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
class happy_home_Adapter (var homeList:List<UsersItem>,var con :Context):RecyclerView.Adapter<happy_home_Adapter.home_items_ViewHolder>(){
    inner class home_items_ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    var image:ImageView = itemView.findViewById(R.id.happy_image)
        var title :TextView = itemView.findViewById(R.id.list_name)
    }

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): home_items_ViewHolder {
         var view =LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
         return home_items_ViewHolder(view)
     }

     override fun getItemCount(): Int {
       return homeList.size
     }

     override fun onBindViewHolder(holder: home_items_ViewHolder, position: Int) {
         Glide.with(con).load(homeList[position].url).into(holder.image)
         holder.title.text = homeList[position].author
     }
 }