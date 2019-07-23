package com.mili.fragment


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import com.mili.R

class LoadingDialogFragment : DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = true
        dialog?.window?.run {
            this.requestFeature(Window.FEATURE_NO_TITLE)
            this.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return inflater.inflate(R.layout.fragment_loading_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(getDialogWidth(), getDialogHeight())
    }

    private fun getDialogWidth(): Int {
        return resources.getDimensionPixelSize(R.dimen.dp_80)
    }

    private fun getDialogHeight(): Int {
        return resources.getDimensionPixelSize(R.dimen.dp_80)
    }
}
