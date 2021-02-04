package com.weddgring

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ezyloop.remot.ApiClass
import com.weddgring.custom.RecycleViewScollListner
import com.weddgring.databinding.FragmentFeedbackBinding
import com.weddgring.pojo.FeedbackPojo
import com.weddgring.pojo.ResponsePojo
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class FeedbackFragment( var recycleViewScollListner: RecycleViewScollListner) : Fragment() {

lateinit var fragmentFeedbackBinding: FragmentFeedbackBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentFeedbackBinding=DataBindingUtil.inflate(inflater ,R.layout.fragment_feedback, container, false)
        // Inflate the layout for this fragment
        initUI()
        recycleViewScollListner.showBottom(false)
        return fragmentFeedbackBinding.root
    }

    private fun initUI() {
      fragmentFeedbackBinding.rlRequest.setOnClickListener {

          var user_id=SharedPreferences.readString(activity!!,SharedPreferences.userid,"");
          var email=SharedPreferences.readString(activity!!,SharedPreferences.email,"");
          var fullname=SharedPreferences.readString(activity!!,SharedPreferences.fullname,"");
          var msg=fragmentFeedbackBinding.etMessage.text.toString();
          if(msg.equals("")){
              Toast.makeText(activity!!,"Please Enter Message",Toast.LENGTH_SHORT).show()
          }else{
              SendConnectionRequest(email,fullname,msg)
          }

      }
    }

    private fun SendConnectionRequest(
        email: String?,
        fullname: String?,
        msg: String
    ) {

        val call: Observable<FeedbackPojo>? = ApiClass.getApiServices(activity).feedback(fullname, email,msg)
        call?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<FeedbackPojo?> {
                override fun onSubscribe(d: Disposable) { //  dialog.dismiss();
                    Log.e("responsePojo", "onSubscribe");

                }

                override fun onNext(responsePojo: FeedbackPojo) {
                    Toast.makeText(activity!!,responsePojo.message,Toast.LENGTH_SHORT).show()
                    fragmentFeedbackBinding.etMessage.setText("")

                }

                override fun onError(e: Throwable) {

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

}
