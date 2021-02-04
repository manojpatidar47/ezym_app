package com.weddgring.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.weddgring.pojo.GetProfilePojo
import com.weddgring.reprository.LoginRepository

class LoginViewModel : ViewModel() {
    var loginPojoMutableLiveData: MutableLiveData<GetProfilePojo>
   // var tokenPojoMutableLiveData: MutableLiveData<ResponsePojo>
    var loginRepository: LoginRepository

    init {
        loginRepository = LoginRepository()
        loginPojoMutableLiveData = MutableLiveData<GetProfilePojo>()
       // tokenPojoMutableLiveData = MutableLiveData<ResponsePojo>()
    }
    fun getLoginPojoMutableLiveData(
        context: Context?,
        email: String?,
        password: String?
    ): MutableLiveData<GetProfilePojo> {
        loginRepository = LoginRepository()
        loginPojoMutableLiveData = MutableLiveData<GetProfilePojo>()

        loginPojoMutableLiveData = loginRepository.getLoginPojoMutableLiveData(context, email, password)
        return loginPojoMutableLiveData
    }

 /*   fun getfbPojoMutableLiveData(
        context: Context?,
        email: String?,
        name: String?,
        mobile:String?,
        pic:String?
    ): MutableLiveData<LoginPojo> {
        loginRepository = LoginRepository()
        loginPojoMutableLiveData = MutableLiveData<LoginPojo>()
        loginPojoMutableLiveData = loginRepository.getFbPojoMutableLiveData(context, email, name,mobile,pic)
        return loginPojoMutableLiveData
    }
    fun gettokenPojoMutableLiveData(
        context: Context?,
        id: String?,
        token: String?

    ): MutableLiveData<ResponsePojo> {
        loginRepository = LoginRepository()
        tokenPojoMutableLiveData = MutableLiveData<ResponsePojo>()
        tokenPojoMutableLiveData = loginRepository.gettokenPojoMutableLiveData(context, id, token)
        return tokenPojoMutableLiveData
    }*/

}