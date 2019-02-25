package com.example.recipes.profile

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.recipes.mainActivity.MainActivity
import com.example.recipes.R
import com.example.recipes.data.model.Card
import com.example.recipes.data.model.Friend
import com.example.recipes.logIn.LoginActivity
import com.example.recipes.mainActivity.MainActivityAdapter
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.Profile
import kotlinx.android.synthetic.main.activity_main.*
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
            setSwipeRefreshLayoutEnabledStatus()
        }
    }

    private fun setLinearLayoutForCardsRecyclerView(cardList: List<Card>?) {
        val adapterCards = MainActivityAdapter(cardList, context)
        myRecipesRecyclerView.adapter = adapterCards
        linearLayoutManagerCards = LinearLayoutManager(this)
        myRecipesRecyclerView.layoutManager = linearLayoutManagerCards
    }

    override fun setSwipeRefreshLayoutEnabledStatus() {
        if(linearLayoutManagerCards != null){
            myRecipesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    swipeRefreshLayoutProfileScreen.isEnabled =
                        linearLayoutManagerCards!!.findFirstVisibleItemPosition() == 0
                }
            })
        }
    }

    override fun setSwipeRefreshLayout() {
        swipeRefreshLayoutProfileScreen.setOnRefreshListener{
            Toast.makeText(context, "Refresh!", Toast.LENGTH_SHORT).show()
        }

        swipeRefreshLayoutProfileScreen?.setColorSchemeResources(R.color.white,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark)
    }

    override fun goMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
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

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
