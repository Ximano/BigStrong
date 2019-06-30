package com.mili.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.lazy.library.logging.Logcat
import com.mili.R

class LifeCycleActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        private const val TAG = "LIFECYCLE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)
        Logcat.d(TAG, "-A--onCreate---")
    }

    override fun onStart() {
        super.onStart()
        Logcat.d(TAG, "-A--onStart---")
    }

    override fun onRestart() {
        super.onRestart()
        Logcat.d(TAG, "-A--onRestart---")
    }

    override fun onResume() {
        super.onResume()
        Logcat.d(TAG, "-A--onResume---")
    }

    override fun onPause() {
        super.onPause()
        Logcat.d(TAG, "-A--onPause---")
    }

    override fun onStop() {
        super.onStop()
        Logcat.d(TAG, "-A--onStop---")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logcat.d(TAG, "-A--onDestroy---")
    }

    override fun onClick(view: View?) {
        view?.run {
            when (this.id) {
                R.id.btn_skip -> startActivity(Intent(this@LifeCycleActivity, SecondActivity::class.java))
                R.id.btn_finish -> finish()
            }
        }
    }
}