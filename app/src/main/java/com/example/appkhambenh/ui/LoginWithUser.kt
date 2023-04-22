package com.example.appkhambenh.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.example.appkhambenh.R
import com.example.appkhambenh.ui.user.QrActivity
import kotlinx.android.synthetic.main.activity_login_with_user.*

class LoginWithUser : AppCompatActivity() {

    companion object{
        const val RESULT = "RESULT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_with_user)

        click()

    }

    private fun click() {
        menuNav.setOnClickListener {
            drawerView.openDrawer(GravityCompat.START)
        }

        qrCode.setOnClickListener {
            val intent: Intent = Intent(this@LoginWithUser,QrActivity::class.java)
            startActivity(intent)
        }

        val result = intent.getStringExtra(RESULT)

        if(result != null) {
            if (result.contains("https://") || result.contains("http://")) {
                val intent: Intent = Intent(Intent.ACTION_VIEW, Uri.parse(result))
                startActivity(intent)
            }else{
                Toast.makeText(this,result.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }
}