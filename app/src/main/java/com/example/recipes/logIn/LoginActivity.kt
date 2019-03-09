package com.example.recipes.logIn

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.recipes.mainScreen.mainUser.MainUserActivity
import com.example.recipes.R
import com.example.recipes.dagger.activity.ActivityModule
import com.example.recipes.dagger.activity.DaggerActivityComponent
import com.example.recipes.dagger.login.DaggerLoginActivityComponent
import com.example.recipes.dagger.login.LoginActivityComponent
import com.example.recipes.dagger.login.LoginActivityModule
import com.example.recipes.mainScreen.mainGuest.MainGuestActivity
import com.example.recipes.utils.UserType
import com.example.recipes.utils.SharedPreferenceManager
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginContract.View{
    @Inject
    lateinit var presenter: LoginContract.Presenter

    @Inject
    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val activityComponent: LoginActivityComponent = DaggerLoginActivityComponent.builder()
            .loginActivityModule(LoginActivityModule(this))
            .activityComponent(DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this)).build())
            .build()

        activityComponent.injectLoginActivity(this)

        presenter.setFirstScreen()
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
