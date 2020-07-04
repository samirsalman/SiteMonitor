package it.salmanapp.sitemonitor.ui.home

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import it.salmanapp.sitemonitor.R
import it.salmanapp.sitemonitor.logic.data.database.Page
import it.salmanapp.sitemonitor.ui.dialogs.AddSiteDialog
import it.salmanapp.sitemonitor.ui.pageDetails.PageDetails
import it.salmanapp.sitemonitor.ui.page_adapters.PageAdapter
import kotlinx.android.synthetic.main.home_fragment.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), AddSiteDialog.NoticeDialogListener, PageItemClickListener {

    override fun onDialogPositiveClick(dialog: DialogFragment, name:String,url:String) {
        Log.d("MAIN_ACTIITY","Confirm ${name}\n${url} ")
        insertData(name,url)
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
        Log.d("MAIN_ACTIITY","Cancel")
    }

    val LOG_HOME_FRAGMENT: String = "HOME_FRAGMENT"




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    lateinit var homeViewModel: HomeViewModel
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = getViewModel()

        page_rows.layoutManager = LinearLayoutManager(context)

        homeViewModel.pages.observe(viewLifecycleOwner, Observer { page ->
            val newsPages = page.filter { it.updates>0 }
            if(newsPages.isEmpty()){
                no_results.visibility=View.VISIBLE
                no_results.playAnimation()
            }else {
                no_results.pauseAnimation()
                no_results.visibility=View.GONE
            }

            val pageAdapter = PageAdapter(newsPages,context, this)
            page_rows.adapter = pageAdapter
        })



        insert_button.setOnClickListener {
            //val page = Page(0, "test", "test", "test", "test", "test")
            showAddSiteDialog()
        }
    }

    private fun insertData(name:String, url:String){
        val viewModel:HomeViewModel by viewModel()

        viewModel.insert(name,url).observe(viewLifecycleOwner, Observer { t ->
            when (t) {
                0 -> {
                    includedLayout.visibility = View.VISIBLE
                }
                1 -> {
                    includedLayout.visibility = View.GONE
                }
                3->{
                    includedLayout.visibility = View.GONE
                    val dialog = AlertDialog.Builder(activity)
                        .setMessage("Errore di rete imprevisto, riprova più tardi o controlla la tua connessione")
                        .setPositiveButton("Ho Capito") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()

                    dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_radius)

                    dialog.show()
                }
                else -> {
                    includedLayout.visibility = View.GONE
                    val dialog = AlertDialog.Builder(activity)
                        .setMessage("L'URL inserito non è valido")
                        .setPositiveButton("Ho Capito") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()

                    dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_radius)

                    dialog.show()
                }
            }
        })

    }

    override fun removeItem(id: Int) {
        homeViewModel.delete(id)
    }

    override fun onPageClick(page: Page) {
        val tempPage = Page(page.id,page.name,page.url,page.hashCode,page.lastUpdate,0)
        homeViewModel.update(tempPage)

        val intent = Intent(activity,PageDetails::class.java)
        intent.putExtra("url",page.url)
        activity?.startActivity(intent)


    }

    private fun showAddSiteDialog() {
        // Create an instance of the dialog fragment and show it
        val dialog = AddSiteDialog()
        dialog.setTargetFragment(this,0)
        dialog.show(parentFragmentManager, "AddSiteFragment")
    }




}