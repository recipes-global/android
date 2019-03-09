package com.example.recipes.profile

import com.example.recipes.data.model.Card
import com.example.recipes.data.model.Friend
import com.facebook.AccessToken
import com.facebook.Profile

interface ProfileContract {
    interface View{
        fun setToolbar()
        fun setFriendsRecyclerView(friendList: List<Friend>?)
        fun setCardsRecyclerView(cardList: List<Card>?)
        fun goMainActivity()
        fun goLoginScreen()
        fun displayProfileInfo(currentProfile: Profile?)
        fun requestEmail(currentAccessToken: AccessToken)
    }

    interface Presenter{
        fun setProfileTracker()
        fun setFirstScreen()
        fun onDestroy()
    }
}