package com.weddgring.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.weddgring.pojo.SignupPojo
import com.weddgring.reprository.SignUpRepository

class SignupViewModel : ViewModel() {
    var signupPojoMutableLiveData: MutableLiveData<SignupPojo>
    var signUpRepository: SignUpRepository
    fun getsignupPojoMutableLiveData(
        context: Context?,
        name: String?,
        email: String?,
        password: String?,
        mobile: String?,
        code:String?
    ): MutableLiveData<SignupPojo> {
        signUpRepository = SignUpRepository()
        signupPojoMutableLiveData = MutableLiveData<SignupPojo>()
        signupPojoMutableLiveData = signUpRepository.getsignupPojoMutableLiveData(context,name, email, password,mobile,code)
        return signupPojoMutableLiveData
    }

    init {
        signUpRepository = SignUpRepository()
        signupPojoMutableLiveData = MutableLiveData<SignupPojo>()
    }
}