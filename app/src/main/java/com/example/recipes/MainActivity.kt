package com.example.recipes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.*
import android.widget.Toast
import org.json.JSONException
import com.facebook.GraphRequest
import com.facebook.AccessToken
import com.bumptech.glide.Glide
import com.facebook.login.LoginManager
import android.content.Intent
import com.example.recipes.logIn.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var  profileTracker: ProfileTracker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setLogoutListener()

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

    private fun requestEmail(currentAccessToken: AccessToken) {
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
        emailTextView.text = email
    }

    private fun goLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun setLogoutListener(){
        logoutButton.setOnClickListener { logout() }
    }

    private fun logout() {
        LoginManager.getInstance().logOut()
        goLoginScreen()
    }

    private fun displayProfileInfo(profile: Profile) {
        val id = profile.id
        val name = profile.name
        val photoUrl = profile.getProfilePictureUri(100, 100).toString()

        nameTextView.text = name
        idTextView.text = id

        Glide.with(applicationContext)
            .load(photoUrl)
            .into(photoImageView)
    }

    override fun onDestroy() {
        super.onDestroy()

        profileTracker.stopTracking()
    }
}
