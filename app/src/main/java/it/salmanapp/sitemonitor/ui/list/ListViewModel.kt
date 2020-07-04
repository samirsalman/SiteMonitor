package it.salmanapp.sitemonitor.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.salmanapp.sitemonitor.logic.data.database.Page
import it.salmanapp.sitemonitor.logic.data.repository.pageRepository.PageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel(pageRepository: PageRepository) : ViewModel() {
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val pages: LiveData<List<Page>>
    private val repository:PageRepository
    init {
        repository = pageRepository
        pages = repository.allPages
    }

    fun delete(id:Int)=viewModelScope.launch(Dispatchers.IO) {
        repository.deletePage(id)
    }

    fun update(page:Page)=viewModelScope.launch(Dispatchers.IO) {
        repository.updatePage(page)
    }



}