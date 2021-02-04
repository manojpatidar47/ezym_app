package com.weddgring.reprository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData

import com.ezyloop.remot.ApiClass
import com.weddgring.pojo.GetProfilePojo
import com.weddgring.pojo.ResponsePojo
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

class LoginRepository {
    var loginPojoMutableLiveData: MutableLiveData<GetProfilePojo> = MutableLiveData<GetProfilePojo>()
    var tokenPojoMutableLiveData: MutableLiveData<ResponsePojo> = MutableLiveData<ResponsePojo>()

    fun getLoginPojoMutableLiveData(
        context: Context?,
        email: String?,
        password: String?
    ): MutableLiveData<GetProfilePojo> {

        var builder = MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("email", email);
        builder.addFormDataPart("password", password);
        var requestBody = builder.build();
        val call: Observable<GetProfilePojo>? = ApiClass.getApiServices(context).user_login(requestBody)
        call?.let {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<GetProfilePojo> {
                override fun onSubscribe(d: Disposable) {
                    Log.e("onnext_login", "subscribe")
                }

                override fun onNext(loginPojo: GetProfilePojo) {
                    Log.e("onnext_login", "complete  "+loginPojo.toString())
                    // Log.e("onnext_login",""+loginPojo.getToken());
                    loginPojoMutableLiveData.setValue(loginPojo)
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
                      /*  try {
                            val jsonObject =
                                JSONObject(responseBody.string())
                            Log.e("onnext_login", jsonObject.getString("msg"))
                            val loginPojo = GetProfilePojo()
                            loginPojo.setStatus(jsonObject.getString("success"))
                            loginPojo.setMsg(jsonObject.getString("msg"))
                            loginPojoMutableLiveData.setValue(loginPojo)
                        } catch (eq: Exception) {
                            val loginPojo = GetProfilePojo()

                            loginPojo.setStatus("Failed")
                            loginPojo.setMsg("Server Error.Please try again!")
                            loginPojoMutableLiveData.setValue(loginPojo)
                            Log.e("onnext_login", eq.message + "    eq")
                        }*/
                    }
                }

                override fun onComplete() {
                    Log.e("onnext_login", "complete")
                }
            })
        }
        return loginPojoMutableLiveData!!
    }

  /*  fun getFbPojoMutableLiveData(
        context: Context?,
        email: String?,
        name: String?,
        mobile:String?,
        pic:String?
    ): MutableLiveData<GetProfilePojo> {
        val call: Observable<GetProfilePojo>? = ApiClass.getApiServices(context).fb_register(email, name,mobile,pic)
        call?.let {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<GetProfilePojo> {
                    override fun onSubscribe(d: Disposable) {
                        Log.e("onnext_login", "subscribe")
                    }

                    override fun onNext(loginPojo: GetProfilePojo) {
                        Log.e("onnext_login", "complete")
                        // Log.e("onnext_login",""+loginPojo.getToken());
                        loginPojoMutableLiveData.setValue(loginPojo)
                    }

                    override fun onError(e: Throwable) {
                        Log.e(
                            "onnext_login",
                            e.message);
                        if (e is HttpException) {
                            val responseBody =
                                e.response()
                                    .errorBody()
                            Log.e(
                                "onnext_login",
                                "$responseBody     responseBody"
                            )
                         *//*   try {
                                val jsonObject =
                                    JSONObject(responseBody.string())
                                Log.e("onnext_login", jsonObject.getString("msg"))
                                val loginPojo = GetProfilePojo()
                                loginPojo.setStatus(jsonObject.getString("success"))
                                loginPojo.setMsg(jsonObject.getString("msg"))
                                loginPojoMutableLiveData.setValue(loginPojo)
                            } catch (eq: Exception) {
                                val loginPojo = GetProfilePojo()

                                loginPojo.setStatus("Failed")
                                loginPojo.setMsg("Server Error.Please try again!")
                                loginPojoMutableLiveData.setValue(loginPojo)
                                Log.e("onnext_login", eq.message + "    eq")
                            }*//*
                        }
                    }

                    override fun onComplete() {
                        Log.e("onnext_login", "complete")
                    }
                })
        }
        return loginPojoMutableLiveData!!
    }*/

   /* fun gettokenPojoMutableLiveData(
        context: Context?,
        id: String?,
        token: String?
    ): MutableLiveData<ResponsePojo> {
        val call: Observable<ResponsePojo>? = ApiClass.getApiServices(context).save_token(id, token)
        call?.let {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResponsePojo> {
                    override fun onSubscribe(d: Disposable) {
                        Log.e("onnext_login", "subscribe")
                    }

                    override fun onNext(responsePojo: ResponsePojo) {
                        Log.e("onnext_login", "complete")
                        // Log.e("onnext_login",""+loginPojo.getToken());
                        tokenPojoMutableLiveData.setValue(responsePojo)
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
                                val loginPojo = LoginPojo()
                                loginPojo.setStatus(jsonObject.getString("success"))
                                loginPojo.setMsg(jsonObject.getString("msg"))
                                loginPojoMutableLiveData.setValue(loginPojo)
                            } catch (eq: Exception) {
                                val loginPojo = LoginPojo()

                                loginPojo.setStatus("Failed")
                                loginPojo.setMsg("Server Error.Please try again!")
                                loginPojoMutableLiveData.setValue(loginPojo)
                                Log.e("onnext_login", eq.message + "    eq")
                            }
                        }
                    }

                    override fun onComplete() {
                        Log.e("onnext_login", "complete")
                    }
                })
        }
        return tokenPojoMutableLiveData!!
    }*/
}