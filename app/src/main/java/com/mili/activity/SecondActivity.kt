package com.mili.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.lazy.library.logging.Logcat
import com.mili.R

class SecondActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        private const val TAG = "LIFECYCLE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Logcat.d(TAG, "-B--onCreate---")
    }

    override fun onStart() {
        super.onStart()
        Logcat.d(TAG, "-B--onStart---")
    }

    override fun onRestart() {
        super.onRestart()
        Logcat.d(TAG, "-B--onRestart---")
    }

    override fun onResume() {
        super.onResume()
        Logcat.d(TAG, "-B--onResume---")
    }

    override fun onPause() {
        super.onPause()
        Logcat.d(TAG, "-B--onPause---")
    }

    override fun onStop() {
        super.onStop()
        Logcat.d(TAG, "-B--onStop---")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logcat.d(TAG, "-B--onDestroy---")
    }

    override fun onClick(view: View?) {
        view?.run {
            when (this.id) {
                R.id.btn_back -> {
                    finish()
                }
            }
        }
    }


}