package it.salmanapp.sitemonitor.ui.page_adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.salmanapp.sitemonitor.R
import it.salmanapp.sitemonitor.logic.data.database.Page
import it.salmanapp.sitemonitor.ui.home.PageItemClickListener


class PageAdapter(data: List<Page>, context: Context?,var clickListener:PageItemClickListener?) :
    RecyclerView.Adapter<PageAdapter.Viewholder>() {
    private var data: List<Page>
    private var model: Page? = null
    private val context: Context

    class Viewholder(itemView: View,clickListener: PageItemClickListener?) : RecyclerView.ViewHolder(itemView) {
        // 1. Declare your Views here
        var siteName: TextView
        var siteUrl: TextView
        var removeButton:ImageButton
        var newsIcon: ImageView

        init {

            // 2. Define your Views here
            siteName = itemView.findViewById(R.id.site_name_row)
            siteUrl = itemView.findViewById(R.id.site_url_row)
            removeButton = itemView.findViewById(R.id.remove_row)
            newsIcon = itemView.findViewById(R.id.newsIcon)

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Viewholder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.page_row, parent, false)
        return Viewholder(itemView,clickListener)
    }

    override fun onBindViewHolder(
        holder: Viewholder,
        position: Int
    ) {
        val model:Page = data[position]
        holder.siteUrl.text = model.url
        holder.siteName.text = model.name
        holder.itemView.setOnClickListener{
            clickListener?.onPageClick(model)
        }
        holder.removeButton.setOnClickListener {
            clickListener?.removeItem(model.id)
        }

        if(model.updates>0) holder.newsIcon.visibility=View.VISIBLE else holder.newsIcon.visibility=View.GONE
    }

    override fun getItemCount(): Int {
        return data.size
    }

    init {
        this.data = data
        this.context = context!!
    }


}
