package com.example.recipes.profile

import com.example.recipes.data.model.Card
import com.example.recipes.data.model.Friend
import com.example.recipes.data.repositories.CardsRepository
import com.facebook.AccessToken
import com.facebook.Profile
import com.facebook.ProfileTracker

class ProfilePresenter(private val profileView: ProfileContract.View,
                       private val cardsRepository: CardsRepository) : ProfileContract.Presenter {
    private lateinit var  profileTracker: ProfileTracker

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

    override fun setFirstScreen() {
        profileView.setToolbar()
        profileView.setFriendsRecyclerView(friendList)
        profileView.setCardsRecyclerView(cardsList)
    }

    override fun onDestroy() {
        profileTracker.stopTracking()
    }

    private val friendList = listOf(
        Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
        Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
        Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
        Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
        Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
        Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png")
    )

    private val cardsList = listOf(
        Card(1, 1, "test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            true, 2, false, 4),
        Card(2, 2, "test2", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            false, 2, true, 4),
        Card(3, 3, "test3", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            true, 2, false, 4),
        Card(4, 4, "test4", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            true, 2, false, 4),
        Card(5, 5, "test5", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png",
            true, 2, false, 4)
    )
}