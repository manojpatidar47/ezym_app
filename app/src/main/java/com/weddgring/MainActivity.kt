package com.weddgring

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.weddgring.custom.RecycleViewScollListner

class MainActivity : AppCompatActivity(),NestedScrollView.OnScrollChangeListener,RecycleViewScollListner{
    lateinit var navigation:BottomNavigationView
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //window.statusBarColor=resources.getColor(android.R.color.transparent)
       window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        )
      // window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        loadFragment(HomeFragment(this,this), "")
         navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
       // super.onBackPressed()
        if( supportFragmentManager.backStackEntryCount>1){
            supportFragmentManager.popBackStack()
        }else{
            finish()
        }

    }

    private fun loadFragment(
        fragment: Fragment,
        name: String
    ) { // load fragment
        val transaction =
            supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contain_home, fragment, name)

        transaction.addToBackStack(name)
        transaction.commit()
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->

            val fragment: Fragment
            val manager = supportFragmentManager
            when (item.itemId) {
                R.id.navigation_people -> {
                    loadFragment(HomeFragment(this,this), "")
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_report -> {
                    loadFragment(SettingFragment(this,this), "")

                    return@OnNavigationItemSelectedListener true
                }
                R.id.request -> {
                    loadFragment(ConnectRequestFragment(this,this), "")

                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onScrollChange(
        v: NestedScrollView?,
        scrollX: Int,
        scrollY: Int,
        oldScrollX: Int,
        oldScrollY: Int
    ) {
        Log.e("NestedScrollView","{$scrollY   $oldScrollY}")
      if(oldScrollY-scrollY>0)
      {
          ObjectAnimator.ofFloat(navigation, "translationY", -10f)
              .apply {
                  duration = 200
                  start()
              }
       /*   if (dy < 0) {

          } else if (dy > 4) {
              */
      }else{
              ObjectAnimator.ofFloat(navigation, "translationY", 400f)
                  .apply {
                      duration = 200
                      start()
                  }
          }
      }

    override fun onScroll(dx: Int, dy: Int) {
        if (dy < 0) {
            ObjectAnimator.ofFloat(navigation, "translationY", -10f)
                .apply {
                    duration = 200
                    start()
                }
        } else if (dy > 4) {
            ObjectAnimator.ofFloat(navigation, "translationY", 400f)
                .apply {
                    duration = 200
                    start()
                }
        }
    }

    override fun showBottom(show: Boolean) {
        if(show){
            ObjectAnimator.ofFloat(navigation, "translationY", -10f)
                .apply {
                    duration = 200
                    start()
                }
        }else{
            ObjectAnimator.ofFloat(navigation, "translationY", 400f)
                .apply {
                    duration = 200
                    start()
                }
        }

    }
}


