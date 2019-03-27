package com.example.recipes.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recipes.dagger.application.DaggerMyApplicationComponent
import com.example.recipes.dagger.profile.DaggerProfileViewModelComponent
import com.example.recipes.dagger.profile.ProfileViewModelComponent
import com.example.recipes.data.model.Card
import com.example.recipes.data.model.Friend
import com.example.recipes.data.repositories.CardsRepository
import com.facebook.AccessToken
import com.facebook.Profile
import com.facebook.ProfileTracker
import com.facebook.login.LoginManager
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel : ViewModel() {
    private lateinit var  profileTracker: ProfileTracker
    private lateinit var disposable: Disposable
    private var cardListLiveData: MutableLiveData<List<Card>> = MutableLiveData()
    private var errorLiveData: MutableLiveData<String> = MutableLiveData()
    private var friendListLiveData: MutableLiveData<List<Friend>> = MutableLiveData()

    @Inject
    lateinit var cardsRepository: CardsRepository

    init {
        val component: ProfileViewModelComponent = DaggerProfileViewModelComponent.builder()
            .myApplicationComponent(DaggerMyApplicationComponent.builder().build())
            .build()

        component.injectProfileViewModel(this)

        setFriendList()
        getCardsFromServer()
    }

    private fun setFriendList(){
        val friendList = listOf(
            Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
            Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
            Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
            Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
            Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png"),
            Friend("test1", "https://cdn.pixabay.com/photo/2013/07/12/12/58/tv-test-pattern-146649_960_720.png")
        )

        friendListLiveData.postValue(friendList)
    }

    fun getCardList(): LiveData<List<Card>> {
        return cardListLiveData
    }

    fun getError(): LiveData<String> {
        return errorLiveData
    }

    fun getFriendList(): LiveData<List<Friend>> {
        return friendListLiveData
    }

    private fun getCardsFromServer(){
        disposable = cardsRepository.getCards().subscribe(
            {
                    cardList -> cardListLiveData.postValue(cardList)
                Timber.tag(TAG).d(cardList.toString())
            },
            {
                    error: Throwable -> errorLiveData.postValue(error.message)
                Timber.tag(TAG).d(error.message)
            }
        )
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}