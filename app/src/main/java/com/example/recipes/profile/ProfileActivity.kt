package com.example.recipes.profile

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.recipes.mainScreen.mainUser.MainUserActivity
import com.example.recipes.R
import com.example.recipes.data.model.Card
import com.example.recipes.data.model.Friend
import com.example.recipes.logIn.LoginActivity
import com.example.recipes.mainScreen.MainActivityAdapter
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.Profile
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONException

class ProfileActivity : AppCompatActivity(), ProfileContract.View {
    private val presenter: ProfileContract.Presenter = ProfilePresenter(this)
    private var context: Context? = null
    private var linearLayoutManagerFriends: LinearLayoutManager? = null
    private var linearLayoutManagerCards: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        context = applicationContext
        presenter.setProfileTracker()
        presenter.setFirstScreen()
    }

    override fun getContext(): Context? {
        return context
    }

    override fun setToolbar() {
        setSupportActionBar(toolbarProfile)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_18dp)
    }

    override fun setFriendsRecyclerView(friendList: List<Friend>?) {
        if(friendList == null){
            Toast.makeText(context, "Brak kart do wyświetlenia!", Toast.LENGTH_SHORT).show()
        }else{
            setLinearLayoutForFriendsRecyclerView(friendList)
        }
    }

    private fun setLinearLayoutForFriendsRecyclerView(friendList: List<Friend>?) {
        val adapterCards = FriendsAdapter(friendList, context)
        friendsRecyclerView.adapter = adapterCards
        linearLayoutManagerFriends = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        friendsRecyclerView.layoutManager = linearLayoutManagerFriends
    }

    override fun setCardsRecyclerView(cardList: List<Card>?) {
        if(cardList == null){
            Toast.makeText(context, "Brak kart do wyświetlenia!", Toast.LENGTH_SHORT).show()
        }else{
            setLinearLayoutForCardsRecyclerView(cardList)
        }
    }

    private fun setLinearLayoutForCardsRecyclerView(cardList: List<Card>?) {
        val adapterCards = MainActivityAdapter(this)
        adapterCards.setCardList(cardList)
        myRecipesRecyclerView.adapter = adapterCards
        linearLayoutManagerCards = LinearLayoutManager(this)
        myRecipesRecyclerView.layoutManager = linearLayoutManagerCards
    }

    override fun goMainActivity() {
        val intent = Intent(this, MainUserActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun goLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun requestEmail(currentAccessToken: AccessToken) {
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
      //  emailTextView.text = email
    }

    override fun displayProfileInfo(profile: Profile?) {
        val name = profile?.name
        val photoUrl = profile?.getProfilePictureUri(100, 100).toString()

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
        val intent = Intent(this, MainUserActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
