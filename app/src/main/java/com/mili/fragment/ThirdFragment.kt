package com.mili.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mili.R
import kotlinx.android.synthetic.main.fragment_second.*

class ThirdFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG", this.javaClass.simpleName + " = onCreateView")
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", this.javaClass.simpleName + "onViewCreated")
        gotoFirstFragmentBtn.setOnClickListener {
            //            Navigation.findNavController(it).navigateUp()
            Toast.makeText(activity, "完成", Toast.LENGTH_LONG).show()
        }
        back.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}