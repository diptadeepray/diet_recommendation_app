import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.app.AlertDialog
import android.content.DialogInterface

class ChoiceDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Build the dialog and return it
        val arr = arrayOf("1", "2", "3", "4")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Diptadeep ")

        var c= "Hello"


        builder.setItems(arr)
        {dialog,which->
            c=arr[which]
        }




        return builder.create()
    }


    //This method will be called when user clicks outside the DialogBox
    //It automatically cancels the dialog box, unless explicitly mentioned the code with Dialog.setCancelable(false)
    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        // Handle what happens when the dialog is canceled
        // For example, show a message or perform an action
        println("Dialog was canceled")
    }



}
