package com.example.roombd

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.CalendarContract.Calendars
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import com.example.roombd.databinding.FragmentAddBinding


class AddFragment : Fragment() {

    lateinit var binding: FragmentAddBinding
    var showTime: String? = null
    var showDate: String? = null

    lateinit var database: NoteDatabase


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddBinding.inflate(inflater, container, false)

        database = Room.databaseBuilder(requireActivity(), NoteDatabase::class.java, "Note-DB")
            .allowMainThreadQueries().build()

        binding.dateBTN.setOnClickListener {
            picADate()
        }

        binding.timeBTN.setOnClickListener {
            picATime()
        }

        binding.subBTN.setOnClickListener {

            picAsub()
        }



        return binding.root
    }

    private fun picAsub() {

        val titlestr = binding.subBTN.text.toString()
        val timeStr = showTime ?: " 00:00"
        val dateStr = showDate ?: " 00/00/0000"


        val note = Note(title = titlestr, time = timeStr, date = dateStr)

        database.getNoteDao().insertData(note)

    }

    private fun picATime() {

        val calendar = Calendar.getInstance()

        val minute = calendar.get(Calendar.MINUTE)
        val hour = calendar.get(Calendar.HOUR)


        val TimePickerDialog = TimePickerDialog(
            requireActivity(), TimePickerDialog.OnTimeSetListener { _, hour, minute ->

                showTime = "$hour:$minute "
                binding.timeBTN.text = showTime


            }, hour, minute, false

        )
        TimePickerDialog.show()
    }

    private fun picADate() {

        val calendar = Calendar.getInstance()

        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val DatePickerDialog = DatePickerDialog(
            requireActivity(),
            DatePickerDialog.OnDateSetListener { _, year, month, dayMonth ->

                showDate = "$year/${month + 1}/$dayMonth"
                binding.dateBTN.text = showDate

            }, year, month, day

        )

        DatePickerDialog.show()


    }

}