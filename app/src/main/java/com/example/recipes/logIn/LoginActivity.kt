package com.example.recipes.logIn

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.recipes.mainScreen.mainUser.MainUserActivity
import com.example.recipes.R
import com.example.recipes.data.repositories.CardsRepository
import com.example.recipes.mainScreen.mainGuest.MainGuestActivity
import com.example.recipes.utils.UserType
import com.example.recipes.utils.SharedPreferenceManager
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginActivityContract.View{

    private val presenter: LoginActivityContract.Presenter =
        LoginActivityPresenter(this, CardsRepository())
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        presenter.setFirstScreen()
    }

    override fun setCallbackManager(){
        callbackManager = CallbackManager.Factory.create()
    }

    override fun setLoginButton(){
        loginButton.setReadPermissions("public_profile")
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                goMainScreen()

            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(applicationContext, "onError", Toast.LENGTH_SHORT).show()
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, "onCancel", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun setNoLoginButton() {
        noLoginButton.setOnClickListener { goNoLoginMainScreen()}
    }

    private fun goMainScreen(){
        SharedPreferenceManager.saveUserInPreferences(applicationContext, UserType.USER)
        val intent = Intent(this, MainUserActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goNoLoginMainScreen(){
        SharedPreferenceManager.saveUserInPreferences(applicationContext, UserType.GUEST)
        val intent = Intent(this, MainGuestActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val TAG = "FacebookLogin"
    }
}
