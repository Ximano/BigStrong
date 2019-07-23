package com.mili.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.mili.R
import kotlinx.android.synthetic.main.activity_shape_use.*

/**
 * @author: strong
 * @date : 2019-07-23
 * @dec : shape文件的使用
 */
class ShapeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shape_use)

        rbLike.setOnClickListener {
            rbLike.isChecked = true
        }

        rbUnLike.setOnClickListener {
            rbUnLike.isChecked = true
        }
    }
}
