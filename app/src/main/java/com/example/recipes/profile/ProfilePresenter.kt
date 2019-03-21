package com.example.recipes.profile

import com.example.recipes.data.model.Friend
import com.example.recipes.data.repositories.CardsRepository
import com.facebook.AccessToken
import com.facebook.Profile
import com.facebook.ProfileTracker
import io.reactivex.disposables.Disposable

class ProfilePresenter(private val profileView: ProfileContract.View,
                       private val cardsRepository: CardsRepository) : ProfileContract.Presenter{
    private lateinit var  profileTracker: ProfileTracker
    private lateinit var disposable: Disposable

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

    private fun getCardsFromServer(){
        disposable = cardsRepository.getCards().subscribe(
            { cardList -> profileView.setCardsRecyclerView(cardList) },
            { error: Throwable -> profileView.showError(error.message) }
        )
    }

    override fun setFirstScreen() {
        getCardsFromServer()
        profileView.setToolbar()
        profileView.setFriendsRecyclerView(friendList)
    }

    override fun onDestroy() {
        profileTracker.stopTracking()
        disposable.dispose()
    }

    private val friendList = listOf(
        Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
        Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
        Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
        Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
        Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
        Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png")
    )
}