package com.rajabi.starwars.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(date :Long) :String{
    val myDate = Date(date)
    val formatDate= SimpleDateFormat("EEE ,d MM  hh:mm aaa" , Locale.getDefault())
    return formatDate.format(myDate)

}