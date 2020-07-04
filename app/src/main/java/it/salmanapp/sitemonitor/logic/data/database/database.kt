package it.salmanapp.sitemonitor.logic.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(entities = [Page::class],version = 1,exportSchema = false )
abstract class PageDatabase: RoomDatabase(){

    //set dao class
    abstract fun pageDao() : PageDao

    companion object {
        @Volatile private var INSTANCE: PageDatabase? = null

        fun getInstance(context: Context):PageDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, PageDatabase::class.java, "page.db").build()
    }

}