package com.astrobyte.orion

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat

class MainActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var videoView: VideoView

    private val splashRunnable = Runnable {
        val intent = Intent(this, HomeActivity::class.java)
        val options = ActivityOptionsCompat.makeCustomAnimation(
            this,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        startActivity(intent, options.toBundle())
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        videoView = findViewById(R.id.videoView)

        val videoUri = Uri.parse("android.resource://$packageName/${R.raw.loadingvid}")
        videoView.setVideoURI(videoUri)

        videoView.setOnPreparedListener { mp ->
            mp.start()
            handler.postDelayed(splashRunnable, 5000)
        }

        videoView.setOnErrorListener { _, what, extra ->
            Log.e("VIDEO_ERROR", "Error: $what, $extra")
            handler.postDelayed(splashRunnable, 1000)
            true
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(splashRunnable)
        videoView.stopPlayback()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(splashRunnable)
        videoView.suspend()
    }
}