package com.example.notesapp_cheezy.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat

@Entity(tableName = "notes_table")
@Parcelize
data class Note(
    var title: String,
    var description: String,
    var created: Long = System.currentTimeMillis(),  //Since we always need to format this timestamp in milliseconds wherever we will use it

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
): Parcelable{
   val createdDateFormated :String get() = DateFormat.getDateTimeInstance().format(created)      //this will take the value of created and format it and give it to us
}