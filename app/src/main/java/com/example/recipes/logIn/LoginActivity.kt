package com.example.recipes.logIn

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.recipes.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class LoginActivity : AppCompatActivity(){
    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        printKeyHash()
        setSignOutListener()
        initializeAuth()
        setCallbackManager()
        setLoginButton()
    }

    private fun printKeyHash(){
        try {
            val info = packageManager.getPackageInfo(
                "com.example.recipes",
                PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }
    }

    private fun initializeAuth(){
        auth = FirebaseAuth.getInstance()
    }

    private fun setCallbackManager(){
        callbackManager = CallbackManager.Factory.create()
    }

    private fun setSignOutListener(){
        buttonFacebookSignout.setOnClickListener { signOutUser() }
    }

    private fun signOutUser(){
        auth.signOut()
        LoginManager.getInstance().logOut()

        updateUI(null)
    }

    private fun setLoginButton(){
        loginButton.setReadPermissions("email", "public_profile")
        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onError(error: FacebookException?) {
                updateUI(null)
            }

            override fun onCancel() {
                updateUI(null)
            }
        })
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken){
        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                }else{
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?){
        if(user != null){
            loginButton.visibility = View.INVISIBLE
            buttonFacebookSignout.visibility = View.VISIBLE
        }else{
            loginButton.visibility = View.VISIBLE
            buttonFacebookSignout.visibility = View.INVISIBLE
        }
    }

    companion object {
        private const val TAG = "FacebookLogin"
    }

}
