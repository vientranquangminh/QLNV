package com.example.quangminh_baikt_sqlite

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class MyArrayAdapter(private val context: Activity, private val myList: ArrayList<Person>)
    : ArrayAdapter<Person>(context, R.layout.my_list, myList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.my_list, null)

        val imgGender: ImageView = view.findViewById(R.id.img_gender)
        val tvInfo: TextView = view.findViewById(R.id.tv_info)
        val checkBox: CheckBox = view.findViewById(R.id.checkbox)
//        val isdelete: CheckBox = view.findViewById(R.id.delete)

        val person = myList[position]
        if(person.gender == 1){
            imgGender.setImageResource(R.drawable.boy)
        }
        else{
            imgGender.setImageResource(R.drawable.girl)
        }

        tvInfo.text = "${person.personID} - ${person.personName}"
//        checkBox.isChecked = false
        checkBox.setOnClickListener {
            person.isDelete =  if(checkBox.isChecked){
                1
            }
            else{
                0
            }
        }

        return view
    }
}