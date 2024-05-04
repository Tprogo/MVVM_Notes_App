package com.example.searchnotesappviewmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.mvvmnotesapp.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        var updateTitile= findViewById<EditText>(R.id.editUpdateTitle)
        var updateDescription= findViewById<EditText>(R.id.editUpdateDescription)
        val update= findViewById<Button>(R.id.updatebutton)

        val existingText = intent.getStringExtra("title")
        val existingDescription = intent.getStringExtra("description")
        val position = intent.getIntExtra("notesposition", 1)

        updateTitile.setText(existingText)
        updateDescription.setText(existingDescription)

        update.setOnClickListener {


            val note = NotesData(id= position, title = updateTitile.text.toString(), description = updateDescription.text.toString())

            GlobalScope.launch(Dispatchers.Main) {




//                 val notesViewModel by viewModels<NotesAdap>()
//
//                val notesAdapter = notesViewModel.notesAdapter
//
//
//                notesAdapter.submitList(updatedList)



            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}