package com.example.recipes.utils

import android.support.design.widget.BottomNavigationView
import android.view.animation.TranslateAnimation

class BottomBarActions {
    companion object {
        fun hideBottomBar(bottomBar: BottomNavigationView){
            val translateAnimation = TranslateAnimation(0f, 0f, 0f, bottomBar.height.toFloat())
            translateAnimation.fillAfter = true
            translateAnimation.duration = 1000
            bottomBar.startAnimation(translateAnimation)
        }

        fun showBottomBar(bottomBar: BottomNavigationView){
            val translateAnimation = TranslateAnimation(0f, 0f, bottomBar.height.toFloat(), 0f)
            translateAnimation.fillAfter = true
            translateAnimation.duration = 1000
            bottomBar.startAnimation(translateAnimation)
        }
    }
}