package com.weddgring

import android.content.Context
import android.content.SharedPreferences

 object SharedPreferences {
    const val PREF_NAME = "my_prefrences"
    const val MODE = Context.MODE_PRIVATE

    const val current_mode = "current_mode"

    const val old_mode = "old_mode"
    const val driving_service_status = "driving_service_status"
    const val selected_trip_id = "selected_trip_id"
    const val selected_local_trip_id = "selected_local_trip_id"
    const val token = "token"
    const val userid = "userid"
    const val firebaseRegid = "firebaseRegid"
    const val email = "email"
    const val fullname = "fullname"
     const val dest_lat = "dest_lat"
     const val dest_lang = "dest_lang"
    const val fingerprintcheck = "fingerprintcheck"
    const val referral_code = "referral_code"
    const val email_forgot = "email_forgot"
    const val user_name = "user_name"
    const val user_image = "user_image"
    const val seleted_group_id = "seleted_group_id"
    const val seleted_group_code = "seleted_group_code"
    const val seleted_group_name = "seleted_group_name"
    const val ifceatednewgroup = "ifceatednewgroup"
    const val current_local_ride_id = "current_local_ride_id"
    const val current_server_ride_id = "current_server_ride_id"
    const val myLocationShareStatus = "myLocationShareStatus"
    const val selected_user_id = "selected_user_id"
    const val selected_user_name = "selected_user_name"
    const val selected_user_image = "selected_user_image"
    const val last_server_ride_id = "last_ride_id"
    const val drive_detection = "drive_detection"
    fun writeBoolean(
        context: Context,
        key: String?,
        value: Boolean
    ) {
        getEditor(context).putBoolean(key, value).commit()
    }

    fun readBoolean(
        context: Context, key: String?,
        defValue: Boolean
    ): Boolean {
        return getPreferences(context).getBoolean(key, defValue)
    }

    fun writeInteger(
        context: Context,
        key: String?,
        value: Int
    ) {
        getEditor(context).putInt(key, value).commit()
    }

    fun readInteger(context: Context, key: String?, defValue: Int): Int {
        return getPreferences(context).getInt(key, defValue)
    }

    fun writeString(
        context: Context,
        key: String?,
        value: String?
    ) {
        getEditor(context).putString(key, value).commit()
    }

    fun readString(
        context: Context,
        key: String?,
        defValue: String?
    ): String? {
        return getPreferences(context).getString(key, defValue)
    }

    fun writeFloat(
        context: Context,
        key: String?,
        value: Float
    ) {
        getEditor(context).putFloat(key, value).commit()
    }

    fun readFloat(
        context: Context,
        key: String?,
        defValue: Float
    ): Float {
        return getPreferences(context).getFloat(key, defValue)
    }

    fun writeLong(
        context: Context,
        key: String?,
        value: Long
    ) {
        getEditor(context).putLong(key, value).commit()
    }

    fun readLong(
        context: Context,
        key: String?,
        defValue: Long
    ): Long {
        return getPreferences(context).getLong(key, defValue)
    }

    fun writeArray(
        context: Context,
        key: String?,
        defValue: Set<String?>?
    ) {
        getEditor(context).putStringSet(key, defValue).commit()
    }

    fun readArray(
        context: Context,
        key: String?,
        defValue: Set<String?>?
    ): Set<String>? {
        return getPreferences(context).getStringSet(key, defValue)
    }

    fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(
            PREF_NAME,
            MODE
        )
    }

    fun getEditor(context: Context): SharedPreferences.Editor {
        return getPreferences(context).edit()
    }
}