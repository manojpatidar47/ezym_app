package com.weddgring

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weddgring.custom.RecycleViewScollListner
import com.weddgring.databinding.FragmentHomeBinding
import com.weddgring.pojo.RecycleViewClickListner
import com.weddgring.pojo.UserListPojo
import com.weddgring.viewmodel.UserlistViewModel

class HomeFragment( var nestedScrollChangeListener: NestedScrollView.OnScrollChangeListener,var recycleViewScollListner: RecycleViewScollListner) : Fragment(),RecycleViewClickListner {
    lateinit var fragmentHomeBinding: FragmentHomeBinding
    lateinit var userlistViewModel: UserlistViewModel
    lateinit var userListPojo: UserListPojo
     var email: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        initUI()
        return fragmentHomeBinding.root
    }

    private fun initUI() {
        email=SharedPreferences.readString(activity!!,SharedPreferences.email,"")
        userlistViewModel=ViewModelProviders.of(this).get(UserlistViewModel::class.java)
        fragmentHomeBinding.shfPaceholderBookDue.startShimmerAnimation()
        userlistViewModel.getUserlist(activity,email).observe(activity!!,object :Observer<UserListPojo>{
            override fun onChanged(t: UserListPojo?) {
                fragmentHomeBinding.shfPaceholderBookDue.stopShimmerAnimation()
                fragmentHomeBinding.shfPaceholderBookDue.visibility=View.GONE
                this@HomeFragment.userListPojo=t!!;
                t?.data?.let {
                    fragmentHomeBinding.rlHome.adapter = HomeAdapter(activity!!,it,this@HomeFragment)
                    fragmentHomeBinding.rlHome.layoutManager = LinearLayoutManager(activity)
                    if(it.size<2){
                        recycleViewScollListner.showBottom(true)
                    }
                }

            }
        })
        //fragmentHomeBinding.rlHome.setOnScrollChangeListener(object :RecyclerView.OnScrollListener(){})
        fragmentHomeBinding.rlHome.setOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.e("onScrolled", dy.toString())
              recycleViewScollListner.onScroll(dx,dy)

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
       /* fragmentHomeBinding.ivProfileImg.setOnClickListener {
            loadFragment(EditFragment(),"")
        }*/
       // fragmentHomeBinding.tvTitle.font
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

    override fun itemClick(position: Int) {
        Log.e("recycleViewClickListner","{$position.toString()}  click fragment")
        loadFragment(UserDetailFragment(userListPojo.data!!.get(position).email,nestedScrollChangeListener),"")
    }

}
