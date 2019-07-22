package com.mili.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.mili.R
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Log.d("TAG", this.javaClass.simpleName + " = onCreateView")
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", this.javaClass.simpleName + "onViewCreated")
        gotoSecondFragmentBtn.setOnClickListener {
            //Navigation.findNavController(it).navigate(R.id.action_firstFragment_to_secondFragment)
            Navigation.findNavController(it).navigate(R.id.action_firstFragment_to_secondFragment, Bundle().apply {
                putString("name", "jenny")
                putInt("age", 18)
            })

        }
    }
}