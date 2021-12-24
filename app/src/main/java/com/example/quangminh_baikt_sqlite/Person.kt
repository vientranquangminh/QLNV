package com.example.quangminh_baikt_sqlite

data class Person(
    val personID: String,
    val personName: String,
    var gender: Int,
    var isDelete: Int = 0
)
