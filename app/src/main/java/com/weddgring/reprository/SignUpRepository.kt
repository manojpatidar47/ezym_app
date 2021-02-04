package com.weddgring.reprository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData

import com.ezyloop.remot.ApiClass
import com.weddgring.pojo.SignupPojo
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class SignUpRepository {
    var signupPojoMutableLiveData: MutableLiveData<SignupPojo> = MutableLiveData<SignupPojo>()

    fun getsignupPojoMutableLiveData(
        context: Context?,
        name: String?,
        email: String?,
        password: String?,
        mobile: String?,code:String?
    ): MutableLiveData<SignupPojo> {
        val call: Observable<SignupPojo>? = ApiClass.getApiServices(context).user_signup(name,email, password)
        call?.let {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Observer<SignupPojo> {
                override fun onSubscribe(d: Disposable) {
                    Log.e("onnext_login", "subscribe")
                }

                override fun onNext(signupPojo: SignupPojo) {
                    Log.e("onnext_login", "complete")
                    // Log.e("onnext_login",""+loginPojo.getToken());
                    signupPojoMutableLiveData.setValue(signupPojo)
                }

                override fun onError(e: Throwable) {
                    if (e is HttpException) {
                        val responseBody =
                            e.response()
                                .errorBody()
                        Log.e(
                            "onnext_login",
                            "$responseBody     responseBody"
                        )
                        try {
                            val jsonObject =
                                JSONObject(responseBody.string())
                            Log.e("onnext_login", jsonObject.getString("msg"))
                            val signupPojo = SignupPojo()
                            signupPojo.status=(jsonObject.getString("success"))
                            signupPojo.msg=(jsonObject.getString("msg"))
                            signupPojoMutableLiveData.setValue(signupPojo)
                        } catch (eq: Exception) {
                            val lsignupPojo = SignupPojo()

                            lsignupPojo.status=("Failed")
                            lsignupPojo.msg=("Server Error.Please try again!")
                            signupPojoMutableLiveData.setValue(lsignupPojo)
                            Log.e("onnext_login", eq.message + "    eq")
                        }
                    }
                }

                override fun onComplete() {
                    Log.e("onnext_login", "complete")
                }
            })
        }
        return signupPojoMutableLiveData!!
    }
}