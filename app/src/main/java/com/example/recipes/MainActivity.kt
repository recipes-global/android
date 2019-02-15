package com.example.recipes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.recipes.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test.setOnClickListener { goProfileScreen() }

    }

    private fun goProfileScreen(){
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
    }
}
