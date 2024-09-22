package com.example.roombd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.roombd.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    lateinit var database: NoteDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)

        database = Room.databaseBuilder(requireActivity(), NoteDatabase::class.java, "Note-DB")
            .allowMainThreadQueries().build()

    val notes : List<Note> =database.getNoteDao().getAllData()

         var adapter = NoteAdapter()
         adapter.submitList(notes)

        binding.addBtn.setOnClickListener {

            findNavController().navigate(R.id.action_homeFragment_to_addFragment)


        }



        return binding.root


    }



}