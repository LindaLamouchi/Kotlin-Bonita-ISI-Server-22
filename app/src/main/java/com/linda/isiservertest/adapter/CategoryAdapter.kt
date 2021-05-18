package com.linda.isiservertest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.linda.isiservertest.R
import com.linda.isiservertest.model.Category
import com.linda.isiservertest.model.ProcessBonita
import kotlinx.android.synthetic.main.row_layout.view.*

class CategoryAdapter(private val listener: CategoryAdapter.OnItemClickListener) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {
    private var myList = emptyList<Category>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): MyViewHolder {

        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.nameProcess.text = myList[position].name
        holder.itemView.version.text = myList[position].createdBy
        holder.itemView.last_update_date.text = myList[position].creation_date
        holder.itemView.idte.text = myList[position].id

    }


    fun setData(newList: List<Category>) {
        myList = newList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}