package com.mili.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mili.R
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG", this.javaClass.simpleName + " = onCreateView")
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", this.javaClass.simpleName + "onViewCreated")
        arguments?.let {
            infoTextView.text = "上页传入数据：name:" + it.getString("name") + ";age:" + it.getInt("age", 0)
        }
        gotoFirstFragmentBtn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_secondFragment_to_thirdFragment)
        }

        back.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}