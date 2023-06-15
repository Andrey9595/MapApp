package ru.anb.mapapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.anb.mapapp.ui.AuthorizationFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AuthorizationFragment()).commit()
    }
}