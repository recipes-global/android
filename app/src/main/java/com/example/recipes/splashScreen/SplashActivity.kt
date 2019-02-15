package com.example.recipes.splashScreen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.recipes.MainActivity
import com.example.recipes.R
import com.example.recipes.logIn.LoginActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity(), SplashContract.View {
    private val splashDisplayLength: Long = 1500
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

    override fun startSplashAnimation() {
        Handler().postDelayed({
            val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.abc_fade_out)
            logo.startAnimation(animation)
        }, splashDisplayLength)

    }
}
