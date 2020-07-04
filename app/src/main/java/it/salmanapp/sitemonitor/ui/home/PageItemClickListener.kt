package it.salmanapp.sitemonitor.ui.home

import it.salmanapp.sitemonitor.logic.data.database.Page

interface PageItemClickListener {

    fun removeItem(id:Int)

    fun onPageClick(page: Page)
}
