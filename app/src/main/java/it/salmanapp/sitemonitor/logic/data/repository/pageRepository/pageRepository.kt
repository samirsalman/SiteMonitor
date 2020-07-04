package it.salmanapp.sitemonitor.logic.data.repository.pageRepository

import android.util.Log
import androidx.lifecycle.LiveData
import io.github.rybalkinsd.kohttp.ext.httpGet
import it.salmanapp.sitemonitor.logic.data.database.Page
import it.salmanapp.sitemonitor.logic.data.database.PageDao
import it.salmanapp.sitemonitor.utils.Hasher


class PageRepository constructor(private val dao:PageDao){
    val allPages: LiveData<List<Page>> = dao.getPages()

    suspend fun insertPage(page:Page) {
        dao.insertPage(page)
    }

    suspend fun updatePage(page: Page) {
        dao.updatePage(page)
    }

    suspend fun deletePage(id: Int){
        dao.delete(id)
    }

    suspend fun getUpdates():Int{

        var updates = 0
        val listOfPages : List<Page> = dao.getPagesSync()
        Log.d("listOfPages",listOfPages.toString())

        for(page in listOfPages) {
            //Log the state
            Log.d("Before doGetRequest()",page.url)

            val content = doGetRequest(page.url)

            Log.d("After doGetRequest()",content)


            if (page.hashCode == Hasher.sha256(content)) {
                updates++
                val tempPage = Page(page.id, page.name, page.url, Hasher.sha256(content), "now",page.updates+1)
                Log.d("HOME_VIEW_MODEL", tempPage.toString())
                dao.updatePage(tempPage)
            }
        }
        return updates

    }


        fun doGetRequest(url:String):String {


            return url.trim().httpGet().body()!!.string()

    }
}