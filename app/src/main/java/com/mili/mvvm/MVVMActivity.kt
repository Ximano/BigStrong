package com.mili.mvvm

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mili.R
import com.mili.databinding.ActivityMvvmBinding
import com.mili.mvvm.viewmodel.MVVMViewModel

class MVVMActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMvvmBinding
    private lateinit var viewModel: MVVMViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm)
        viewModel = ViewModelProviders.of(this).get(MVVMViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}