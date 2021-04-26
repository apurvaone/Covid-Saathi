    package com.example.covidsaathi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StateAdapter(private val listener:StateItemClicked): RecyclerView.Adapter<StateViewHolder>() {
    private  val items:ArrayList<State> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateViewHolder {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        val viewHolder= StateViewHolder(view)

        view.setOnClickListener {
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: StateViewHolder, position: Int) {
        val currentItem= items[position]
        holder.stateName.text=currentItem.stateName
        holder.confirmed.text=currentItem.confirmed
        holder.active.text=currentItem.active
        holder.recovered.text=currentItem.recoverd
        holder.death.text=currentItem.death
        holder.lastupdated.text=currentItem.lastUpdated

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateNews(updatedNews: ArrayList<State>)
    {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }
}

class StateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
    val stateName:TextView=itemView.findViewById(R.id.ststeName)
    val confirmed:TextView=itemView.findViewById(R.id.total_cases)
    val active:TextView=itemView.findViewById(R.id.active_cases)
    val recovered:TextView=itemView.findViewById(R.id.recovered)
    val death:TextView=itemView.findViewById(R.id.death)
    val lastupdated:TextView=itemView.findViewById(R.id.last_updated)

}


    interface StateItemClicked{
        fun onItemClicked(item:State)
    }