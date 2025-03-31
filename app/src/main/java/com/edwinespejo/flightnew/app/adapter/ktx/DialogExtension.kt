package com.edwinespejo.flightnew.app.adapter.ktx

import android.app.Activity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import com.edwinespejo.flightnew.app.R


object DialogExtension {
    fun Activity.showCustomAlertDialog(
        message: String,
        title: String? = null,
        onClickPositive: () -> Unit,
    ) {
        var alertDialog: AlertDialog? = null
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        val dialogView = layoutInflater.inflate(R.layout.dialog_minimal, null)
        val textView = dialogView.findViewById<TextView>(R.id.dialogMessage)
        textView.apply {
            text = HtmlCompat.fromHtml(
                message,
                HtmlCompat.FROM_HTML_MODE_COMPACT
            )
        }
        dialogView.findViewById<LinearLayout>(R.id.lnyAuth).visibility = View.GONE
        dialogView.findViewById<Button>(R.id.okButton).setOnClickListener {
            alertDialog?.dismiss()
            onClickPositive()
        }
        val txtTitle = dialogView.findViewById<TextView>(R.id.txtTitle)
        if (!title.isNullOrEmpty()) {
            txtTitle.visibility = View.VISIBLE
            txtTitle.text = title
        } else {
            txtTitle.visibility = View.GONE
        }

        builder.setView(dialogView)
        alertDialog = builder.create()
        alertDialog.show()

    }

    fun Activity.showAuthAlertDialog(
        message: String, title: String? = null,
        onClickPositive: () -> Unit,
        onClickNegative: () -> Unit = { }
    ) {
        var alertDialog: AlertDialog? = null
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
        val dialogView = layoutInflater.inflate(R.layout.dialog_minimal, null)
        dialogView.findViewById<LinearLayout>(R.id.lnyAuth).visibility = View.VISIBLE
        dialogView.findViewById<TextView>(R.id.dialogMessage).text = message
        dialogView.findViewById<Button>(R.id.okButton).visibility = View.GONE


        dialogView.findViewById<Button>(R.id.btnAccept).setOnClickListener {
            alertDialog?.dismiss()
            onClickPositive()
        }
        dialogView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            alertDialog?.dismiss()
            onClickNegative()
        }

        val txtTitle = dialogView.findViewById<TextView>(R.id.txtTitle)
        if (!title.isNullOrEmpty()) {
            txtTitle.visibility = View.VISIBLE
            txtTitle.text = title
        } else {
            txtTitle.visibility = View.GONE
        }

        builder.setView(dialogView)
        alertDialog = builder.create()
        alertDialog.show()

    }
}
