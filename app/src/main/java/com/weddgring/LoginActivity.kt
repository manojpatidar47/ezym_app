package com.weddgring

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.ezyloop.remot.ApiClass
import com.google.android.material.snackbar.Snackbar
import com.weddgring.databinding.ActivityLoginBinding
import com.weddgring.pojo.GetProfilePojo
import com.weddgring.viewmodel.LoginViewModel
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.skyfishjy.library.RippleBackground
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var activityLoginBinding: ActivityLoginBinding;
    private lateinit var dialog: Dialog
    lateinit var loginViewModel: LoginViewModel
    lateinit var user_id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        user_id = SharedPreferences.readString(this@LoginActivity, SharedPreferences.userid, "").toString()
        if(!user_id.equals("")){
            startActivity(
                Intent(
                    this@LoginActivity,
                    MainActivity::class.java
                )
            )
            finishAffinity()
//Log.e("LoginActivity","")
        }
        /*activityLoginBinding.llLogin.setOnClickListener {
            this
           // startActivity(Intent(this@LoginActivity, MainActivity::class.java))
          //  startActivity(Intent(this,MainActivity::class.java))
        }*/
      /*  var shapedwable=activityLoginBinding.etEmail.background as GradientDrawable
        shapedwable.setColor(Color.parseColor("#000000"))*/
        activityLoginBinding.llLogin.setOnClickListener(this)
        activityLoginBinding.tvDont.setOnClickListener(this)
        //setContentView(R.layout.activity_login)

    }

    private fun setupdata() {


        val call: Observable<GetProfilePojo>? =
            ApiClass.getApiServices(this).getProfile("john@gmail.com")
        call?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<GetProfilePojo?> {
                override fun onSubscribe(d: Disposable) { //  dialog.dismiss();
                    Log.e("responsePojo", "onSubscribe");
                }

                override fun onNext(getProfilepojo: GetProfilePojo) {

                    // refreshFragemnt()
                    // Log.e("userupdatepojo", registerPojo.getMsg().toString() + "nnn")
                    Log.e("responsePojo", "responsePojo " + getProfilepojo.toString());
//registerPojoMutableLiveData.setValue(registerPojo);
                }

                override fun onError(e: Throwable) {
                    dialog!!.dismiss()
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
                    dialog!!.dismiss()
                    // refreshFragemnt()
                    Log.e("userupdatepojo", "complete")
                }
            })
    }

    open fun showdialog() { // Toast.makeText(context, "showDialog", Toast.LENGTH_SHORT).show();
        dialog = Dialog(this)
        dialog?.let {
            it.apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(R.layout.dialog_layout)
                val dialogText = findViewById(R.id.dialog_tv) as TextView
                dialogText.text = "Please wait...."
                val content = findViewById(R.id.content) as RippleBackground
                content.startRippleAnimation()
                setCancelable(false)
                getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                );
                window?.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                );
                show()
            }
        } ?: run {
            Toast.makeText(this, "Dailog null", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_dont -> {
                // shareItem("https://i.imgur.com/tGbaZCY.jpg");
                startActivity(Intent(this, SignupActivity::class.java))
            }
            R.id.tv_forgotPassword -> {
                // startActivity(Intent(this, ForgotPassword::class.java))
            }
            R.id.ll_login -> {
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

                Log.e(
                    "ed_email_add",
                    activityLoginBinding.etEmail.getText().toString().indexOf(' ').toString()
                )

                if (activityLoginBinding.etEmail.getText().toString() == "" || activityLoginBinding.etEmail.getText().toString().indexOf(
                        ' '
                    ) >= 0
                ) {
                    activityLoginBinding.etEmail.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_error_rounded))
                    Snackbar.make(activityLoginBinding.clMain, "Please Enter Email", 2000).show()
                }/* else if (!(activityLoginBinding.etEmail.getText().toString().trim().matches(
                        emailPattern.toRegex()
                    ))
                ) {
                    activityLoginBinding.etEmail.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_error_rounded))
                    Snackbar.make(activityLoginBinding.clMain, "Please Enter Valid Email", 2000)
                        .show()
                } */ else {
                    activityLoginBinding.etEmail.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_rounded))

                    if (activityLoginBinding.etPassword.getText().toString() == "") {
                        activityLoginBinding.etPassword.setBackgroundDrawable(
                            resources.getDrawable(
                                R.drawable.background_white_full_error_rounded
                            )
                        )
                        Snackbar.make(activityLoginBinding.clMain, "Please Enter Password", 2000)
                            .show()
                    } else {
                        activityLoginBinding.etPassword.setBackgroundDrawable(
                            resources.getDrawable(
                                R.drawable.background_white_full_rounded
                            )
                        )
                        showdialog()
                        Log.d(
                            "logincred",
                            activityLoginBinding.etEmail.getText().toString() + "  " + activityLoginBinding.etPassword.getText().toString()
                        )

                        loginViewModel.getLoginPojoMutableLiveData(
                            this,
                            activityLoginBinding.etEmail.getText().toString(),
                            activityLoginBinding.etPassword.getText().toString()
                        )
                            .observe(this, object : androidx.lifecycle.Observer<GetProfilePojo> {
                                override fun onChanged(t: GetProfilePojo?) {
                                    dialog?.let {
                                        it.dismiss()
                                    }
                                    if (t!!.status.equals("200")) {
                                        Log.e("userID", t.data!!.id.toString() + "login")
                                        SharedPreferences.writeString(
                                            this@LoginActivity,
                                            SharedPreferences.userid,
                                            t.data!!.id.toString()

                                        )
                                        SharedPreferences.writeString(
                                            this@LoginActivity,
                                            SharedPreferences.email,
                                            t.data!!.email

                                        )
                                        SharedPreferences.writeString(
                                            this@LoginActivity,
                                            SharedPreferences.fullname,
                                            t.data!!.fullname

                                        )
                                        startActivity(
                                            Intent(
                                                this@LoginActivity,
                                                MainActivity::class.java
                                            )
                                        )
                                        finishAffinity()
//Log.e("LoginActivity","")
                                        //addFirebaseToken(t.getData()!!.customer_id)
                                    } else {
                                        Snackbar.make(
                                            activityLoginBinding.clMain,
                                            t.message.toString(),
                                            2000
                                        )
                                            .show()

                                    }

                                }
                            })
                    }
                }

                //
                ///  showdialog()

            }
        }

    }
}
