package com.puzzlebench.clean_marvel_kotlin.animation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.puzzlebench.clean_marvel_kotlin.ANIMATION_DURATION
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.SPLASH_DURATION
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.IntermediateTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_indefinitely)
        animation.duration = ANIMATION_DURATION
        image_rotation.startAnimation(animation)

        Timer().schedule(object :TimerTask(){
            override fun run() {
                val intent = Intent(this@SplashActivity,MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        },SPLASH_DURATION)
    }
}
