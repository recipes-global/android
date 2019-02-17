package com.example.recipes.profile

import com.facebook.AccessToken
import com.facebook.Profile
import com.facebook.ProfileTracker

class ProfilePresenter(private val profileView: ProfileContract.View) : ProfileContract.Presenter {
    private lateinit var  profileTracker: ProfileTracker

    override fun configView() {
        profileView.setLogoutListener()

    }

    override fun setProfileTracker(){
        profileTracker = object : ProfileTracker() {
            override fun onCurrentProfileChanged(oldProfile: Profile?, currentProfile: Profile?) {
                if (currentProfile != null) {
                    profileView.displayProfileInfo(currentProfile)
                }
            }
        }

        if(AccessToken.getCurrentAccessToken() == null){
            profileView.goLoginScreen()
        }else{
            profileView.requestEmail(AccessToken.getCurrentAccessToken())

            val profile = Profile.getCurrentProfile()
            if(profile != null){
                profileView.displayProfileInfo(profile)
            }else{
                Profile.fetchProfileForCurrentAccessToken()
            }
        }
    }

    override fun onDestroy() {
        profileTracker.stopTracking()
    }
}