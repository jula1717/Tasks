package com.example.tasks2

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat
@Entity(tableName = "tasks_table")
@Parcelize
data class Task (val name:String,val important:Boolean=false,val done:Boolean=false, val date:Long = System.currentTimeMillis(),@PrimaryKey (autoGenerate = true) val id: Int = 0) : Parcelable{
    val dateFormated = DateFormat.getDateInstance().format(date)
}