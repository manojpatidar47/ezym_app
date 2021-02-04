package com.weddgring

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import com.weddgring.custom.RecycleViewScollListner
import com.weddgring.databinding.FragmentSettingBinding


class SettingFragment(var nestedScrollChangeListener: NestedScrollView.OnScrollChangeListener, var recycleViewScollListner: RecycleViewScollListner) : Fragment() {
    lateinit var fragmentSettingBinding: FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSettingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        // Inflate the layout for this fragment
        initUI();
        return fragmentSettingBinding.root
    }

    private fun initUI() {
        recycleViewScollListner.showBottom(true)
        fragmentSettingBinding.tvEditProfile.setOnClickListener {
            loadFragment(EditFragment(nestedScrollChangeListener,recycleViewScollListner), "EditFragment")
        }
        fragmentSettingBinding.tvFeedback.setOnClickListener {
            loadFragment(FeedbackFragment(recycleViewScollListner), "FeedbackFragment")
        }
        fragmentSettingBinding.tvLogout.setOnClickListener {
            SharedPreferences.writeString(activity!!, SharedPreferences.userid, "")
            startActivity(Intent(activity!!, LoginActivity::class.java))
            activity!!.finishAffinity()
        }
    }

    private fun loadFragment(
        fragment: Fragment,
        name: String
    ) { // load fragment
        val transaction =
            activity!!.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contain_home, fragment, name)

        transaction.addToBackStack(name)
        transaction.commit()
    }
}
