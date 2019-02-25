package com.example.recipes.splashScreen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.recipes.mainActivity.MainActivity
import com.example.recipes.R
import com.example.recipes.logIn.LoginActivity

class SplashActivity : AppCompatActivity(), SplashContract.View {
    private val presenter: SplashContract.Presenter = SplashPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter.checkUser()
    }

    override fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
