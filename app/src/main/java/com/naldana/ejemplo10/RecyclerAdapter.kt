package com.naldana.ejemplo10

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.items.view.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
//import kotlin.collections.ArrayList<Item>?

class RecyclerAdapter(money: ArrayList<Money>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {


    var items: ArrayList<Money>?= null


            init {
                items=money
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val vista = LayoutInflater.from(parent?.context).inflate(R.layout.items,parent,false)
        return ViewHolder(vista)
    }


    override fun getItemCount(): Int {
        return items?.size!!
    }


    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val item=items?.get(position)
        holder.nombre?.text=item?.name
        holder.foto?.setImageResource(item?.image!!)
    }

    class ViewHolder(v:View):RecyclerView.ViewHolder(v) {
        var vista=v
        var foto:ImageView?=null
        var nombre:TextView?=null

        init{
            foto = vista.iv_image
            nombre=vista.tv_name
        }


    }
}

