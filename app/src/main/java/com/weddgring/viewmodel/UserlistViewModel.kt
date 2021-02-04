package com.weddgring.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.weddgring.pojo.UserListPojo
import com.weddgring.reprository.UserListRepository

class UserlistViewModel : ViewModel() {
    var userlistmutableLivedta: MutableLiveData<UserListPojo>
    // var tokenPojoMutableLiveData: MutableLiveData<ResponsePojo>

    var userListRepository: UserListRepository;

    init {
        userListRepository = UserListRepository()
        userlistmutableLivedta = MutableLiveData<UserListPojo>()
        // tokenPojoMutableLiveData = MutableLiveData<ResponsePojo>()
    }

    fun getUserlist(
        context: Context?,
        email: String?
    ): MutableLiveData<UserListPojo> {
        userListRepository = UserListRepository()
        userlistmutableLivedta = MutableLiveData<UserListPojo>()

        userlistmutableLivedta = userListRepository.userlistmustable(context, email)
        return userlistmutableLivedta
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