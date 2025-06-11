package com.astrobyte.orion

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnIsiAbsen: MaterialButton = findViewById(R.id.btnIsiAbsen)
        val btnDataSiswa: Button = findViewById(R.id.btnDataSiswa)
        val btnRekapAbsen: Button = findViewById(R.id.btnRekapAbsen)

        btnIsiAbsen.setOnClickListener {
            val intent = Intent(this, IsiAbsenActivity::class.java)
            startActivity(intent)
        }

        btnDataSiswa.setOnClickListener {
            val intent = Intent(this, DataSiswaActivity::class.java)
            startActivity(intent)
        }

        btnRekapAbsen.setOnClickListener {
            val intent = Intent(this, RekapAbsenActivity::class.java)
            startActivity(intent)
        }
    }
}