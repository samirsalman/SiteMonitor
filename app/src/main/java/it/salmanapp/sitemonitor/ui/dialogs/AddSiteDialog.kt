package it.salmanapp.sitemonitor.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.rengwuxian.materialedittext.MaterialEditText
import it.salmanapp.sitemonitor.R
import java.util.*

class AddSiteDialog : DialogFragment(){

    // Use this instance of the interface to deliver action events
    internal lateinit var listener: NoticeDialogListener

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface NoticeDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment,name:String,url:String)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = targetFragment as NoticeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT))

        val rootView : View = inflater.inflate(R.layout.add_page_dialog,container,false)
        val confirmButton = rootView.findViewById<Button>(R.id.confirm_add_page)
        val cancelButton = rootView.findViewById<Button>(R.id.close_add_page)

        val nameText = rootView.findViewById<MaterialEditText>(R.id.page_name)
        val urlText = rootView.findViewById<MaterialEditText>(R.id.page_url)

        confirmButton.setOnClickListener {
            val nameTextString: String = nameText.text.toString().trim()
            val urlTextString: String = urlText.text.toString().trim()

            if (nameTextString.isEmpty() || urlTextString.isEmpty()) {
                if (nameTextString.isEmpty()) {
                    nameText.hint = "Il campo non può essere vuoto"
                    nameText.setHintTextColor(Color.RED)
                }

                if (urlTextString.isEmpty()) {
                    urlText.hint = "Il campo non può essere vuoto"
                    urlText.setHintTextColor(Color.RED)
                }
            } else {
                listener.onDialogPositiveClick(
                    this, nameTextString,
                    urlText.text.toString().trim().toLowerCase(Locale.getDefault())
                )
                dialog?.dismiss()
            }
        }

        cancelButton.setOnClickListener {
            listener.onDialogNegativeClick(this)
            dialog?.dismiss()
        }

        return rootView
    }

}