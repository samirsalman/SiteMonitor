package it.salmanapp.sitemonitor.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import it.salmanapp.sitemonitor.logic.data.database.Page
import it.salmanapp.sitemonitor.logic.data.database.PageDatabase
import it.salmanapp.sitemonitor.logic.data.repository.pageRepository.PageRepository
import it.salmanapp.sitemonitor.utils.Hasher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.ConnectException


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PageRepository
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val pages: LiveData<List<Page>>

    init {
        val pageDao = PageDatabase.getInstance(application).pageDao()
        repository = PageRepository(pageDao)
        pages = repository.allPages
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(name:String,url:String):LiveData<Int> = liveData(Dispatchers.IO){
        emit(0)
        val content:String
        try {
        content = repository.doGetRequest(url)
        val page = Page(0,name,url,Hasher.sha256(content),"now",0)
        Log.d("HOME_VIEW_MODEL",page.toString())
        repository.insertPage(page)
            emit(1)
        }catch(e:IllegalArgumentException ){
            Log.d("Retrofit Web Call",e.message.toString())
            emit(2)
        }catch (e:Exception){
            emit(3)
        }

    }


    fun delete(id:Int)=viewModelScope.launch(Dispatchers.IO) {
        repository.deletePage(id)
    }

    fun update(page:Page)=viewModelScope.launch(Dispatchers.IO) {
        repository.updatePage(page)
    }
}