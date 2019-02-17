package com.example.recipes.profile

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.recipes.MainActivity
import com.example.recipes.R
import com.example.recipes.logIn.LoginActivity
import com.facebook.AccessToken
import com.facebook.GraphRequest
import com.facebook.Profile
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.activity_profile.*
import org.json.JSONException

class ProfileActivity : AppCompatActivity(), ProfileContract.View {

    private val presenter: ProfileContract.Presenter = ProfilePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        presenter.configView()
        presenter.setProfileTracker()
    }

    override fun goMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
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
        emailTextView.text = email
    }

    override fun goLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun setLogoutListener(){
        logoutButton.setOnClickListener { logout() }
    }

    private fun logout() {
        LoginManager.getInstance().logOut()
        goLoginScreen()
    }

    override fun displayProfileInfo(profile: Profile?) {
        val id = profile?.id
        val name = profile?.name
        val photoUrl = profile?.getProfilePictureUri(100, 100).toString()

        nameTextView.text = name
        idTextView.text = id

        Glide.with(applicationContext)
            .load(photoUrl)
            .into(photoImageView)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
