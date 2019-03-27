package com.example.recipes.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.recipes.mainScreen.mainUser.MainUserActivity
import com.example.recipes.R
import com.example.recipes.data.model.Card
import com.example.recipes.data.model.Friend
import com.example.recipes.logIn.LoginActivity
import com.example.recipes.MainCardsAdapter
import com.example.recipes.dagger.activity.ActivityModule
import com.example.recipes.dagger.activity.DaggerActivityComponent
import com.example.recipes.dagger.profile.DaggerProfileActivityComponent
import com.example.recipes.dagger.profile.ProfileActivityModule
import com.example.recipes.dagger.profile.ProfileActivityComponent
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.Profile
import com.facebook.ProfileTracker
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONException
import timber.log.Timber
import javax.inject.Inject

class ProfileActivity : AppCompatActivity() {
    private lateinit var  profileTracker: ProfileTracker

    @Inject
    lateinit var linearLayoutManagerCards: androidx.recyclerview.widget.LinearLayoutManager

    @Inject
    lateinit var adapterCards: MainCardsAdapter

    @Inject
    lateinit var adapterFriends: FriendsAdapter

    @Inject
    lateinit var profileViewModel: ProfileViewModel

    private var linearLayoutManagerFriends: LinearLayoutManager? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setDaggerComponent()
        setViewModelObserver()
        setProfileTracker()
        setToolbar()
    }

    private fun setDaggerComponent() {
        Timber.tag(TAG).d("setDaggerComponent")
        val activityComponent: ProfileActivityComponent = DaggerProfileActivityComponent.builder()
            .profileActivityModule(ProfileActivityModule(this))
            .activityComponent(DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this)).build())
            .build()

        activityComponent.injectProfileActivity(this)
    }

    private fun setViewModelObserver(){
        Timber.tag(TAG).d("setViewModelObserver")
        profileViewModel.getCardList().observe(this, Observer { setCardsRecyclerView(it) })
        profileViewModel.getFriendList().observe(this, Observer { setFriendsRecyclerView(it) })
        profileViewModel.getError().observe(this, Observer { showError(it) })
    }

    private fun setProfileTracker(){
        profileTracker = object : ProfileTracker() {
            override fun onCurrentProfileChanged(oldProfile: Profile?, currentProfile: Profile?) {
                if (currentProfile != null) {
                    displayProfileInfo(currentProfile)
                }
            }
        }

        if(AccessToken.getCurrentAccessToken() == null){
            goLoginScreen()
        }else{
            requestEmail(AccessToken.getCurrentAccessToken())

            val profile = Profile.getCurrentProfile()
            if(profile != null){
                displayProfileInfo(profile)
            }else{
                Profile.fetchProfileForCurrentAccessToken()
            }
        }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbarProfile)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_18dp)
    }

    private fun setFriendsRecyclerView(friendList: List<Friend>?) {
        if(friendList == null){
            Toast.makeText(applicationContext, "Brak kart do wyświetlenia!", Toast.LENGTH_SHORT).show()
        }else{
            adapterFriends.setFriendsList(friendList)
            setLinearLayoutForFriendsRecyclerView()
        }
    }

    private fun setLinearLayoutForFriendsRecyclerView() {
        friendsRecyclerView.adapter = adapterFriends
        linearLayoutManagerFriends = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
            false
        )
        friendsRecyclerView.layoutManager = linearLayoutManagerFriends
    }

    private fun setCardsRecyclerView(cardList: List<Card>?) {
        if(cardList == null){
            Toast.makeText(applicationContext, "Brak kart do wyświetlenia!", Toast.LENGTH_SHORT).show()
        }else{
            adapterCards.setCardList(cardList)
            setLinearLayoutForCardsRecyclerView()
        }
    }

    private fun setLinearLayoutForCardsRecyclerView() {
        myRecipesRecyclerView.adapter = adapterCards
        myRecipesRecyclerView.layoutManager = linearLayoutManagerCards
    }

    private fun goMainActivity() {
        val intent = Intent(this, MainUserActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun requestEmail(currentAccessToken: AccessToken) {
        Timber.tag(TAG).d("requestEmail: $currentAccessToken")
        val request = GraphRequest.newMeRequest(currentAccessToken,
            GraphRequest.GraphJSONObjectCallback { `object`, response ->
                if (response.error != null) {
                    Toast.makeText(applicationContext, response.error.errorMessage, Toast.LENGTH_LONG).show()
                    return@GraphJSONObjectCallback
                }
                try {
                    val email = `object`.getString("email")
                    setEmail(email)
                } catch (e: JSONException) {
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                }
            })
        val parameters = Bundle()
        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location")
        request.parameters = parameters
        request.executeAsync()
    }

    private fun setEmail(email: String) {
        this.email = email
    }

    private fun displayProfileInfo(currentProfile: Profile?) {
        val name = currentProfile?.name
        val photoUrl = currentProfile?.getProfilePictureUri(100, 100).toString()

        nameTextView.text = name

        Glide.with(applicationContext)
            .load(photoUrl)
            .into(photoImageView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.settings, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.app_bar_settings_profile -> Toast.makeText(applicationContext, "SETTINGS", Toast.LENGTH_SHORT).show()
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    override fun onBackPressed() {
        goMainActivity()
    }

    private fun showError(errorMessageText: String?) {
        Toast.makeText(this, errorMessageText, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        profileTracker.stopTracking()
    }

    companion object {
        private const val TAG = "ProfileActivity"
    }
}
