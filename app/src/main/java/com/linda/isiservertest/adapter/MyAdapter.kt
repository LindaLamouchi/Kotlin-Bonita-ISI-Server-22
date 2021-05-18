package com.linda.isiservertest.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.linda.isiservertest.R
import com.linda.isiservertest.model.ProcessBonita
import kotlinx.android.synthetic.main.row_layout.view.*

class MyAdapter( private val listener:OnItemClickListener): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private var myList = emptyList<ProcessBonita>()

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),
    View.OnClickListener
    {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position:Int=adapterPosition
            if (position!=RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): MyViewHolder {

        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.itemView.nameProcess.text=myList[position].name
        holder.itemView.version.text=myList[position].version
        holder.itemView.last_update_date.text=myList[position].last_update_date
        holder.itemView.idte.text=myList[position].id.toString()

    }



    fun setData(newList: List<ProcessBonita>){
        myList = newList
        notifyDataSetChanged()
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}