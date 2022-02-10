package com.example.screentime.dialogs

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.screentime.R
import java.lang.ClassCastException

/**
 * Handles the Android Dialog for picking a limit with a NumberPicker
 */
private const val TAG = "com.example.screentime.dialogs.dialoglimitpicker"
class DialogLimitPicker() : AppCompatDialogFragment()
{
    private lateinit var numberpickerAppLimit: NumberPicker
    private lateinit var listener : DialogLimitPickerListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog
    {
        return activity?.let {
            val builder : AlertDialog.Builder = AlertDialog.Builder(it)

            // Get the layout inflater
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            val view: View = inflater.inflate(R.layout.dialog_limit_picker, null)

            val bundle = arguments

            builder.setView(view)
                .setTitle("Limit Picker")
                // Add action buttons
                .setPositiveButton(R.string.btn_limit_confirm,
                                   DialogInterface.OnClickListener { dialog, id ->
                                       val limit = numberpickerAppLimit.value
                                       listener.applyLimit(limit)
                                   })
                .setNegativeButton(R.string.btn_limit_delete,
                                   DialogInterface.OnClickListener { dialog, id ->
                                       listener.applyLimit(-1)
                                       //getDialog()?.cancel()
                                   })

            numberpickerAppLimit = view.findViewById(R.id.numberpickerAppLimit)
            numberpickerAppLimit.minValue = 0
            numberpickerAppLimit.maxValue = 1439

            if(bundle != null)
            {
                val limit = bundle.getInt("Limit")
                if(limit == -1){
                    numberpickerAppLimit.value = 0
                }
                else
                {
                    numberpickerAppLimit.value = limit
                }
            }else
            {
                numberpickerAppLimit.value = 0
            }


            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        try
        {
            listener = context as DialogLimitPickerListener
        }catch (e: ClassCastException)
        {
            throw ClassCastException("${context.toString()} DialogLimitPickerListener has to be implemented")
        }
    }

    interface DialogLimitPickerListener
    {
        fun applyLimit(limit: Int)
    }
}