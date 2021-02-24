package com.example.innowisegrouptask.ui.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.example.innowisegrouptask.R

class LoadingDialogImpl(private val activity : Activity) : LoadingDialog {
    private lateinit var  dialog : AlertDialog
    override fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.loading_dialog,null))
            .setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    override fun dismissLoadingDialog() {
        dialog.dismiss()
    }

}