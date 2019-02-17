package com.example.recipes.profile

import com.facebook.AccessToken
import com.facebook.Profile

interface ProfileContract {
    interface View{
        fun goMainActivity()
        fun goLoginScreen()
        fun setLogoutListener()
        fun displayProfileInfo(currentProfile: Profile?)
        fun requestEmail(currentAccessToken: AccessToken)
    }

    interface Presenter{
        fun configView()
        fun setProfileTracker()
        fun onDestroy()
    }
}