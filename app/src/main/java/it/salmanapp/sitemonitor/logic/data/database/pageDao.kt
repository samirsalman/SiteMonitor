package it.salmanapp.sitemonitor.logic.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PageDao{

    @Query("SELECT * FROM PAGES")
    fun getPages(): LiveData<List<Page>>


    @Query("SELECT * FROM PAGES")
    suspend fun getPagesSync(): List<Page>

    @Query("SELECT * FROM PAGES WHERE name LIKE :name")
    fun getById(name:String): LiveData<Page>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPage(page:Page)


    @Query("DELETE FROM PAGES WHERE id= :id")
    suspend fun delete(id:Int):Int

    @Update
    suspend fun updatePage(page:Page)

    @Query("DELETE FROM PAGES")
    suspend fun deleteAll()



}