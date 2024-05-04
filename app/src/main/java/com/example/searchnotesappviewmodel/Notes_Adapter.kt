package com.example.searchnotesappviewmodel


import android.app.Activity
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmnotesapp.R

class Notes_Adapter  : androidx.recyclerview.widget.ListAdapter<NotesData, Notes_Adapter.NotesViewHolder>(NoteDiffCallback()){
    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.titletext)
        val titlenum = itemView.findViewById<TextView>(R.id.titlenumber)
        val description = itemView.findViewById<TextView>(R.id.descriptiontext)
        val editbtn = itemView.findViewById<ImageView>(R.id.icedit)
        val deletebtn = itemView.findViewById<ImageView>(R.id.icdelete)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return NotesViewHolder(view)
    }

//    we dont use noteslist in diffutil

//    override fun getItemCount(): Int {
//        return notesList.size
//    }

    private  var itemPositionListener: itemposition? = null

    fun setItemPositionListener(listener: itemposition) {
        itemPositionListener = listener
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val notes = getItem(position)


        // Context for itemview. Application context will not woek here
        val context = holder.itemView.context
        //


        holder.titlenum.text = (position + 1).toString()
        holder.title.text = notes.title
        holder.description.text = notes.description




        holder.editbtn.setOnClickListener {

            //holder.itemView.context is used to get the Context associated with the itemView of the ViewHolder.
            // Then, startActivity(intent) is called using this Context.

            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("notesposition", notes.id)
            intent.putExtra("title", notes.title)
            intent.putExtra("description", notes.description)
            context.startActivity(intent)
        }



        holder.deletebtn.setOnClickListener {
            // Create NotesData object and pass it as parameter to deletNotes method
            val dltnotes =
                NotesData(id = notes.id, title = notes.title, description = notes.description)

            //use coroutines for methods otherwise app will not work




                itemPositionListener?.currentNote(dltnotes)

            val intent = Intent(holder.itemView.context, MainActivity::class.java)
            holder.itemView.context.startActivity(intent)
            (holder.itemView.context as Activity).finish()





        }
    }



}