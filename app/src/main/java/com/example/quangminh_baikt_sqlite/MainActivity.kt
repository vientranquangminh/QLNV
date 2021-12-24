package com.example.quangminh_baikt_sqlite

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var edtMaNV: EditText
    private lateinit var edtTenNV: EditText
    private lateinit var btnAdd: Button

    private lateinit var rdbtnMale: RadioButton
    private lateinit var rdbtnFemale: RadioButton

    private lateinit var imgbtnDelete: ImageButton
    private lateinit var listView: ListView

    private lateinit var arr: ArrayList<Person>
    private lateinit var myAdapter: MyArrayAdapter

    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtMaNV = findViewById(R.id.edt_manv)
        edtTenNV = findViewById(R.id.edt_tennv)
        btnAdd = findViewById(R.id.btn_nhap)

        rdbtnFemale = findViewById(R.id.radiobtn_female)
        rdbtnMale = findViewById(R.id.radiobtn_male)

        imgbtnDelete = findViewById(R.id.imgbtn_delete)

        listView = findViewById(R.id.lv)

        database = openOrCreateDatabase("qlnv", MODE_PRIVATE, null)
        try {
            createTable()
        } catch (e: Exception){
            Log.e("Error", "Table is exists")
        }

        arr = arrayListOf()
        readFromDatabase()
//        arr.add(Person("1", "Vien Tran Quang Minh", 1))
//        arr.add(Person("2", "Vien Tran Quang", 0))
//        arr.add(Person("3", "Vien Tran", 1))
//        arr.add(Person("4", "Vien", 0))

        myAdapter = MyArrayAdapter(this, arr)
        listView.adapter = myAdapter
        myAdapter.notifyDataSetChanged()

        btnAdd.setOnClickListener {
            addRecord()
            readFromDatabase()
        }

        imgbtnDelete.setOnClickListener{
            deleteRows()
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createTable(){
        val table ="CREATE TABLE person(id TEXT, name TEXT, gender INTEGER)"
        database.execSQL(table)
    }

    private fun addRecord(){
        val personId = edtMaNV.text.toString()
        val personName = edtTenNV.text.toString()
        val personGender = if (rdbtnMale.isChecked){
            1
        }
        else{
            0
        }

        val values: ContentValues = ContentValues()
        values.put("id", personId)
        values.put("name", personName)
        values.put("gender", personGender)
        var msg: String = ""

        msg = if (database.insert("person", null, values).toInt() == -1){
            "Fail to insert record"
        } else{
            "Insert record successfully"
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun readFromDatabase(){
        arr.clear()
        val cursor: Cursor = database.query("person", null, null, null, null, null, null)
        cursor.moveToFirst()
        var data = ""
        while (!cursor.isAfterLast){
            arr.add(Person(cursor.getString(0), cursor.getString(1), cursor.getInt(2)))
            cursor.moveToNext()
        }
        cursor.close()
        myAdapter = MyArrayAdapter(this, arr)
        listView.adapter = myAdapter
        myAdapter.notifyDataSetChanged()
    }

    private fun deleteRows(){
        var res: String = "";
        for(i in arr){
            if(i.isDelete == 1){
                val delId = i.personID
                val del = database.delete("person", "id=?", arrayOf(delId))
                var msg: String = if (del == 0){
                    "Delete row $delId fail"
                }
                else{
                    "Delete row $delId successful"
                }
                res += msg
            }
        }
        myAdapter.notifyDataSetChanged()
        readFromDatabase()
    }

}