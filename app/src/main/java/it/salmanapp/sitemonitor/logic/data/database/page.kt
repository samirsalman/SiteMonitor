package it.salmanapp.sitemonitor.logic.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pages")
data class Page(

    @PrimaryKey(autoGenerate = true) val id: Int,

    //name of the page eventually given by user
    @ColumnInfo(name="name")
    var name : String,

    //url of the page
    @ColumnInfo(name="url")
    var url : String,

    //hash of the content
    @ColumnInfo(name="hashCode")
    var hashCode : String,

    //date of last update
    @ColumnInfo(name="lastUpdate")
    var lastUpdate : String,

    //number of updates
    @ColumnInfo(name="updates")
    var updates:Int

)