package com.example.recipes.utils

import android.content.Context
import android.preference.PreferenceManager

class sharedPreferenceMenager {

    companion object {
        fun saveUserInPreferences(context: Context, userType: UserType){
            val userTypeShared = PreferenceManager.getDefaultSharedPreferences(context).edit()
            userTypeShared.putString(Constant.USER_TYPE, userType.toString()).apply()
            userTypeShared.commit()
        }

        fun getUserInPreferences(context: Context): UserType{
            val userTypeString = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(Constant.USER_TYPE, UserType.GUEST.toString())
            return if(userTypeString == UserType.USER.toString()){
                UserType.USER
            }else {
                UserType.GUEST
            }
        }
    }

}