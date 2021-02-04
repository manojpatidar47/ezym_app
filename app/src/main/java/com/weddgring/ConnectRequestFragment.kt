package com.weddgring

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ezyloop.remot.ApiClass
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.weddgring.custom.RecycleViewScollListner
import com.weddgring.databinding.FragmentConnectRequestBinding
import com.weddgring.pojo.ConnectRequestPojo
import com.weddgring.pojo.RecycleViewClickListner
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class ConnectRequestFragment(var nestedScrollChangeListener: NestedScrollView.OnScrollChangeListener, var recycleViewScollListner: RecycleViewScollListner) : Fragment(),
    RecycleViewClickListner {
  lateinit var fragmentConnectionRequestBinding: FragmentConnectRequestBinding
    lateinit var connectRequestPojo: ConnectRequestPojo
    lateinit var user_id: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentConnectionRequestBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_connect_request, container, false)
        initUI()
        return fragmentConnectionRequestBinding.root
    }

    private fun initUI() {
        user_id = SharedPreferences.readString(activity!!, SharedPreferences.userid, "").toString();
        getConnectionRequest(user_id)
    }

    private fun getConnectionRequest(userId: String) {

        val call: Observable<ConnectRequestPojo>? =
            ApiClass.getApiServices(activity).connect_request(userId)
        call?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<ConnectRequestPojo?> {
                override fun onSubscribe(d: Disposable) { //  dialog.dismiss();
                    Log.e("responsePojo", "onSubscribe");
                    fragmentConnectionRequestBinding.shfPaceholderCoonect.startShimmerAnimation()
                }

                override fun onNext(connectRequestPojo: ConnectRequestPojo) {
                    fragmentConnectionRequestBinding.shfPaceholderCoonect.stopShimmerAnimation()
                    fragmentConnectionRequestBinding.shfPaceholderCoonect.visibility=View.GONE
                    this@ConnectRequestFragment.connectRequestPojo=connectRequestPojo!!;
                    connectRequestPojo?.data?.let {
                        fragmentConnectionRequestBinding.rlRequest.adapter =
                            ConnectionRequestAdapter(activity!!, it, this@ConnectRequestFragment)
                        fragmentConnectionRequestBinding.rlRequest.layoutManager =
                            LinearLayoutManager(activity)
                        if (connectRequestPojo.data.size < 3) {
                            recycleViewScollListner.showBottom(true)
                        }
                        if(connectRequestPojo.data.size==0){
                            fragmentConnectionRequestBinding.tvNorequest.visibility=View.VISIBLE
                        }else{
                            fragmentConnectionRequestBinding.tvNorequest.visibility=View.GONE
                        }
                    }
//registerPojoMutableLiveData.setValue(registerPojo);
                }

                override fun onError(e: Throwable) {
                    //fragmentConnectionRequestBinding.shfPaceholderCoonect.stopShimmerAnimation()
                   // fragmentConnectionRequestBinding.shfPaceholderCoonect.visibility=View.GONE
                   // fragmentConnectionRequestBinding.tvNorequest.visibility=View.VISIBLE

                    //dialog!!.dismiss()
                    // refreshFragemnt()
                    if (e is HttpException) {
                        val responseBody = e.response().errorBody()
                        Log.e(
                            "onnext_login",
                            "$responseBody     responseBody"
                        )
                        /*  try {
                              val jsonObject = JSONObject(responseBody.string())
                              Log.e("onnext_login", jsonObject.getString("msg"))
                              val loginPojo = LoginPojo()
                              loginPojo.setSuccess(jsonObject.getString("success"))
                              loginPojo.setMsg(jsonObject.getString("msg"))
                              Toast.makeText(
                                  activity,
                                  "" + jsonObject.getString("msg"),
                                  Toast.LENGTH_SHORT
                              ).show()
                          } catch (eq: java.lang.Exception) {
                              val loginPojo = LoginPojo()
                              loginPojo.setSuccess("0")
                              loginPojo.setMsg("Server Error.Please try again!")
                              Toast.makeText(
                                  activity,
                                  "Server Error.Please try again!",
                                  Toast.LENGTH_SHORT
                              ).show()
                              Log.e("onnext_login", eq.message + "    eq")
                          }*/
                    }
                    //   registerPojoMutableLiveData.setValue(loginPojo);
                    Log.e("userupdatepojo", "" + e.message)
                }

                override fun onComplete() {
                    // dialog!!.dismiss()
                    // refreshFragemnt()
                    Log.e("userupdatepojo", "complete")
                }
            })
    }

    override fun itemClick(position: Int) {
      //  Log.e("itemconnectrequest",connectRequestPojo.data!!.get(position).email)
       loadFragment(UserDetailFragment(connectRequestPojo.data!!.get(position).email,nestedScrollChangeListener),"")
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
