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
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import javax.inject.Inject

class ProfileViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private var cardListLiveData: MutableLiveData<List<Card>> = MutableLiveData()
    private var errorLiveData: MutableLiveData<String> = MutableLiveData()
    private var friendListLiveData: MutableLiveData<List<Friend>> = MutableLiveData()
    private var friendCountLiveData: MutableLiveData<Int> = MutableLiveData()

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
        friendCountLiveData.postValue(friendList.size)
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

    fun getFriendCount(): LiveData<Int> {
        return friendCountLiveData
    }

    private fun getCardsFromServer(){
        val disposable = cardsRepository.getCards().subscribe(
            {
                    cardList -> cardListLiveData.postValue(cardList)
                Timber.tag(TAG).d(cardList.toString())
            },
            {
                    error: Throwable -> errorLiveData.postValue(error.message)
                Timber.tag(TAG).d(error.message)
            }
        )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}