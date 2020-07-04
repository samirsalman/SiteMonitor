package it.salmanapp.sitemonitor.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import it.salmanapp.sitemonitor.R
import it.salmanapp.sitemonitor.logic.data.database.Page
import it.salmanapp.sitemonitor.ui.home.PageItemClickListener
import it.salmanapp.sitemonitor.ui.pageDetails.PageDetails
import it.salmanapp.sitemonitor.ui.page_adapters.PageAdapter
import kotlinx.android.synthetic.main.list_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ListFragment : Fragment(), PageItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }
    private lateinit var viewModel:ListViewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
         viewModel= getViewModel()

        list_rows.layoutManager = LinearLayoutManager(context)

        viewModel.pages.observe(viewLifecycleOwner, Observer { page ->
            val pageAdapter = PageAdapter(page,context,this)
            list_rows.adapter = pageAdapter

        })
    }

    override fun removeItem(id: Int) {
        viewModel.delete(id)
    }

    override fun onPageClick(page: Page) {
        val tempPage = Page(page.id,page.name,page.url,page.hashCode,page.lastUpdate,0)
        viewModel.update(tempPage)

        val intent = Intent(activity,PageDetails::class.java)
        intent.putExtra("url",page.url)
        activity?.startActivity(intent)
    }

}