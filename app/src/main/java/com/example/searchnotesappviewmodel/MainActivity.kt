package com.example.searchnotesappviewmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmnotesapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, MenuProvider, itemposition {

    private val notesViewModel by viewModels<NotesViewModel>()
    val notesAdapter = Notes_Adapter()

    private lateinit var notesRecyclerView: RecyclerView // Declare RecyclerView as a member variable
    private lateinit var no_notes_layout: View



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
//
        setSupportActionBar(toolbar)

        val menuHost: MenuHost = this
        menuHost.addMenuProvider(this, this, Lifecycle.State.RESUMED)


        // getting the recyclerview by its id
        notesRecyclerView = findViewById(R.id.recyclerview1)
        no_notes_layout = findViewById(R.id.no_notesdata)

        notesAdapter.setItemPositionListener(this)





        // this creates a vertical layout Manager

        // create layout manager for reverse order
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true






        notesRecyclerView.layoutManager = linearLayoutManager




        val position = intent.getIntExtra("notesposition", 0)





        lifecycleScope.launch(Dispatchers.Main) {
            val notelist = notesViewModel.getAllNotes()

            Log.d("Get Notes", "Getting Data Called: ${notelist.toString()}")


        }


        notesViewModel.notesLiveData.observe(this@MainActivity, Observer { noteList->
            notesAdapter.submitList(noteList)

            if (noteList.isNotEmpty()){
                setVisibility(true)
            }else{
                setVisibility(false)
            }

            Log.d("TAG", "Data ${noteList.size}")

        })

        notesRecyclerView.adapter = notesAdapter
//
//        val p1 = NotesData(2,"Titlegshshs","wjejje")
//        val p2 = NotesData(15,"New Post","Check Diffutil Result")
//
//
//
//
//        Handler(Looper.getMainLooper()).postDelayed(Runnable {
//            notesAdapter.submitList(listOf(p1,p2))
//        }, 4000)


//        val noteslist2 = ArrayList<NotesData>()
//        noteslist2.add(NotesData(1, "Titleenej","nenen" ))
//        noteslist2.add(NotesData(2,"Title","nsnsne"))
//        noteslist2.add(NotesData(6,"Titlemsms", "jjj2jj2"))
//        noteslist2.add(NotesData(7,"Seventh Title","Seventh Description"))
//        noteslist2.add(NotesData(8,"Eighth Title","Eighth Description"))
//
//
//        Handler(Looper.getMainLooper()).postDelayed(Runnable {
//          notesAdapter.submitList(noteslist2)
//        }, 4000)

        //exit app when back button is pressed

        val onBackPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
            }

        }

        onBackPressedDispatcher.addCallback(this, onBackPressCallback)


        //floating action button

        val floatingButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        floatingButton.setOnClickListener {


            val intent = Intent(this, AddNotesActivity::class.java)


            startActivity(intent)


        }


    }



    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.isSubmitButtonEnabled = false
        searchView.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        lifecycleScope.launch(Dispatchers.Main) {
            if (newText != null) {
                searchNotes(newText.lowercase())
                notesAdapter.notifyDataSetChanged()
            }
        }

        return true
    }

    private fun searchNotes(query: String?){
        val searchQuery = "%$query%"
        lifecycleScope.launch(Dispatchers.Main){
            notesViewModel.searchNote(searchQuery)
        }

//        notesViewModel.notesLiveData.observe(this){ list->
//            notesAdapter.submitList(list)
//
//        }
    }

    private fun setVisibility(flag: Boolean){

        if (flag){
            no_notes_layout.visibility = View.GONE
            notesRecyclerView.visibility = View.VISIBLE

        } else{
            no_notes_layout.visibility = View.VISIBLE
            notesRecyclerView.visibility = View.GONE
        }
    }

    override fun currentNote(note: NotesData) {
        lifecycleScope.launch(Dispatchers.Main){
        notesViewModel.deletNotes(note)}
    }


}