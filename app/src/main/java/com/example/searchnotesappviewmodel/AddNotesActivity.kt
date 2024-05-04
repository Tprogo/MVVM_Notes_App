package com.example.searchnotesappviewmodel

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.mvvmnotesapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
@AndroidEntryPoint
class AddNotesActivity : AppCompatActivity() {

    private val notesViewModel by viewModels<NotesViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnotse)

        //Hide keyboard when touch outside of edit text
        //use this code in oncreate start


        val rootView = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        rootView.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                // Hide the keyboard when the user clicks outside any EditText
                closeKeyBoard(rootView)
            }
            false
        }



        // Initialize the database


        val mainHeading = findViewById<TextView>(R.id.textView1)
        val addTitleText = findViewById<EditText>(R.id.addTitleText)
        val addescription = findViewById<EditText>(R.id.addescription)
        val saveNotesBtn = findViewById<Button>(R.id.buttonsave)

        //hide keybaord when click outside of edit text field





        saveNotesBtn.setOnClickListener {
            // Get text from EditText
            val title = addTitleText.text.toString()
            val description = addescription.text.toString()

            // Check if title and description are not empty
            if (title.isNotEmpty() && description.isNotEmpty()) {


                // Create a NotesData object
                val notes = NotesData(id = null, title = title, description = description)

//                val NotesDao= db.getNotesDao()

                lifecycleScope.launch(Dispatchers.Main){
                    notesViewModel.insertNotes(notes)


                }

                val notesAdapter = Notes_Adapter()

                GlobalScope.launch(Dispatchers.Main){
                    notesViewModel.getAllNotes()
                }













                Toast.makeText(this, "Note is added", Toast.LENGTH_SHORT).show()

                // Clear EditText fields
                addTitleText.text.clear()
                addescription.text.clear()

                notesViewModel.notesLiveData.observe(this, Observer {newList->

                    notesAdapter.submitList(newList)

                    Log.d("Not Added","New Note: ${newList.size}")
                })

                // Navigate back to MainActivity
                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Title and description cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }




    // create this function outside of oncreate method

    fun closeKeyBoard(view: View){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
    }
}