package com.weddgring

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.google.android.material.snackbar.Snackbar
import com.weddgring.databinding.ActivitySignupBinding
import com.weddgring.pojo.SignupPojo
import com.weddgring.viewmodel.LoginViewModel
import com.weddgring.viewmodel.SignupViewModel
import com.skyfishjy.library.RippleBackground


class SignupActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var activitySignupBinding: ActivitySignupBinding
    lateinit var signupViewModel: SignupViewModel
    private lateinit var dialog: Dialog
    private val RC_SIGN_IN = 9001
/*    private var mGoogleApiClient: GoogleApiClient? = null
    private var callbackManager: CallbackManager? = null*/
    lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //FacebookSdk.sdkInitialize(this)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        initUI();

        //shareItem("https://homepages.cae.wisc.edu/~ece533/images/airplane.png");
    }

    private fun initUI() {
        signupViewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        activitySignupBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        activitySignupBinding.llSignup.setOnClickListener(this)
        //googleLogin()
       // fbLogin()
        activitySignupBinding.llGoogle.setOnClickListener (this)
        activitySignupBinding.llFb.setOnClickListener (this)
        activitySignupBinding.tvDont.setOnClickListener (this)
    }
  /*  private fun fbLogin() {
        callbackManager = CallbackManager.Factory.create()
        activitySignupBinding.fbButton.registerCallback(callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    result?.accessToken
                    result?.recentlyDeniedPermissions
                    result?.recentlyGrantedPermissions
                }

                override fun onCancel() {
                    Snackbar.make(activitySignupBinding.clMain, "Cancelled by User", 2000)
                        .show()

                }

                override fun onError(error: FacebookException?) {
                    Snackbar.make(activitySignupBinding.clMain, error?.message.toString(), 2000)
                        .show()
                }


            }
        )
    }
    private fun googleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, null)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

    }*/
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_signup -> {
                signupCall();
            }
           R.id.tv_dont->{
               startActivity(Intent(this,LoginActivity::class.java))
            }
            /* R.id.ll_fb->{
                activitySignupBinding.fbButton.performClick()
            }*/
        }
    }

    private fun signupCall() {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val f_name: String = activitySignupBinding.etName.getText().toString()
        val first_bool = f_name.matches(".*[a-zA-Z]+.*".toRegex())


        val email: String = activitySignupBinding.etEmail.getText().toString()
        val password: String = activitySignupBinding.etPassword.getText().toString()
        val phone: String = activitySignupBinding.etPhonenumber.getText().toString()
        val code: String = activitySignupBinding.etCode.getText().toString()
        val passbool = password.matches("[A-Za-z0-9]+".toRegex())

        if (f_name == "") {
            activitySignupBinding.etName.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_error_rounded))
            Snackbar.make(activitySignupBinding.clMain, "Please Enter  Name", 1000).show()
        } else if (!first_bool) {
            activitySignupBinding.etName.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_error_rounded))
            Snackbar.make(activitySignupBinding.clMain, "Please Enter First Name", 1000).show()
        } else {
            activitySignupBinding.etName.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_rounded))
            if (email == "") {
                activitySignupBinding.etEmail.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_error_rounded))
                Snackbar.make(activitySignupBinding.clMain, "Please Enter Email", 1000).show()
            } else if (!email.matches(emailPattern.toRegex())) {
                activitySignupBinding.etEmail.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_error_rounded))
                Snackbar.make(activitySignupBinding.clMain, "Please Enter Valid  Email", 1000)
                    .show()
            } else {
                activitySignupBinding.etEmail.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_rounded))

              /*  if (phone == "") {
                    activitySignupBinding.etPhonenumber.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_error_rounded))
                    Snackbar.make( activitySignupBinding.clMain, "Please Enter Phone Number", 1000).show()
                } else if (phone.length < 10) {
                    activitySignupBinding.etPhonenumber.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_error_rounded))
                    Snackbar.make( activitySignupBinding.clMain, "Please Enter Correct Phone Number", 1000).show()
                } else {
                    activitySignupBinding.etPhonenumber.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_rounded))*/
                    if (password == "") {
                        activitySignupBinding.etPassword.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_error_rounded))
                        Snackbar.make(activitySignupBinding.clMain, "Please Enter Password", 1000)
                            .show()
                    } else if (password.length < 5) {
                        activitySignupBinding.etPassword.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_error_rounded))
                        Snackbar.make(
                            activitySignupBinding.clMain,
                            "Password Should Be Atleast Of 5 Character ",
                            1000
                        ).show()
                    } else if (password.contains(" ")) {
                        activitySignupBinding.etPassword.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_error_rounded))
                        Snackbar.make(
                            activitySignupBinding.clMain,
                            "Password Should Not Contains Spaces",
                            1000
                        ).show()
                    } /*else if (!passbool) {
                            ed_password.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_edit_text_signup_error));
                            Snackbar.make(c_main, "Password should contain only  Characters and Numbers", 1000).show();

                        } */ else {
                        activitySignupBinding.etPassword.setBackgroundDrawable(resources.getDrawable(R.drawable.background_white_full_rounded))
                        showdialog()
                        // val userPojo = UserPojo(f_name, l_name, email, password, c_pass, encoded_image)
                        signupViewModel.getsignupPojoMutableLiveData(this, f_name, email, password, phone,code).observe(this,object:Observer<SignupPojo>{
                            override fun onChanged(t: SignupPojo?) {
                                dialog?.let {
                                    it.dismiss()
                                }
                                if(t!!.status.equals("200")){

                                    startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                                }else{
                                    Snackbar.make(activitySignupBinding.clMain, t.msg.toString(), 2000)
                                        .show()

                                }
                            }
                        })

                    }

               // }


            }
        }

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
/*    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        } else {

            try {
                getUserProfile(
                    AccessToken.getCurrentAccessToken().token,
                    AccessToken.getCurrentAccessToken().userId,
                    AccessToken.getCurrentAccessToken().applicationId
                )
            } catch (e: Exception) {
            }
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            result?.let {
                it.signInAccount?.let {
                    Log.e("GoogleSignInResult", "${it.displayName} ${it.email} ${it.photoUrl}")
                    showdialog()
                    loginViewModel.getfbPojoMutableLiveData(
                        this,
                        it.email,
                        it.displayName,
                        "",
                        it.photoUrl.toString()
                    )
                        .observe(this, object : Observer<LoginPojo> {
                            override fun onChanged(t: LoginPojo?) {
                                dialog?.let {
                                    it.dismiss()
                                }
                                if (t!!.getStatus().equals("success")) {
                                    SharedPreferences.writeString(
                                        this@SignupActivity,
                                        SharedPreferences.userid,
                                        t.getData()!!.customer_id

                                    )
                                    SharedPreferences.writeString(
                                        this@SignupActivity,
                                        SharedPreferences.email,
                                        t.getData()!!.email

                                    )
                                    SharedPreferences.writeString(
                                        this@SignupActivity,
                                        SharedPreferences.referral_code,
                                        t.getData()!!.referral_code

                                    )
                                    addFirebaseToken(t.getData()!!.customer_id);
                                } else {
                                    Snackbar.make(
                                        activitySignupBinding.clMain,
                                        t.getMsg().toString(),
                                        2000
                                    )
                                        .show()

                                }

                            }
                        })
                }

            }

        }
    }
    private fun getUserProfile(
        token: String,
        userId: String,
        applicationId: String
    ) {
        val accessToken =
            AccessToken(token, applicationId, userId, null, null, null, null, null)
        val request = GraphRequest.newMeRequest(
            accessToken
        ) { `object`, response ->
            Log.e("GraphRequest", `object`.toString())
            try {
                val first_name = `object`.getString("first_name")
                val last_name = `object`.getString("last_name")
                val email = `object`.getString("email")
                val id = `object`.getString("id")
                val image_url =
                    "https://graph.facebook.com/$id/picture?type=normal"
                *//*    txtUsername.setText("First Name: " + first_name + "\nLast Name: " + last_name);
                                        txtEmail.setText(email);
                                        Picasso.with(MainActivity.this).load(image_url).into(imageView);*//*

                showdialog()
                loginViewModel.getfbPojoMutableLiveData(
                    this,email,
                    first_name+" "+last_name,
                    "",
                    image_url
                )
                    .observe(this, object : Observer<LoginPojo> {
                        override fun onChanged(t: LoginPojo?) {
                            dialog?.let {
                                it.dismiss()
                            }
                            if (t!!.getStatus().equals("success")) {
                                Log.e("loginid", t.getData()!!.customer_id);
                                SharedPreferences.writeString(
                                    this@SignupActivity,
                                    SharedPreferences.userid,
                                    t.getData()!!.customer_id

                                )
                                SharedPreferences.writeString(
                                    this@SignupActivity,
                                    SharedPreferences.email,
                                    t.getData()!!.email

                                )
                                SharedPreferences.writeString(
                                    this@SignupActivity,
                                    SharedPreferences.referral_code,
                                    t.getData()!!.referral_code

                                )
                                addFirebaseToken(t.getData()!!.customer_id);

                            } else {
                                Snackbar.make(
                                    activitySignupBinding.clMain,
                                    t.getMsg().toString(),
                                    2000
                                )
                                    .show()

                            }

                        }
                    })
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val parameters = Bundle()
        parameters.putString("fields", "first_name,last_name,email,id")
        request.parameters = parameters
        request.executeAsync()
    }
    private fun addFirebaseToken(customerId: String?) {
        showdialog()
        var regid = SharedPreferences.readString(this, SharedPreferences.firebaseRegid, "")
        Log.e("addFirebaseToken", "${customerId}  ${regid}")
        loginViewModel.gettokenPojoMutableLiveData(
            this,
            customerId,
            regid
        )
            .observe(this, object : Observer<ResponsePojo> {
                override fun onChanged(t: ResponsePojo?) {
                    dialog?.let {
                        it.dismiss()
                    }
                    startActivity(
                        Intent(
                            this@SignupActivity,
                            HomeActivity::class.java
                        )
                    )
                    finishAffinity()
                }
            })

    }*/
}
