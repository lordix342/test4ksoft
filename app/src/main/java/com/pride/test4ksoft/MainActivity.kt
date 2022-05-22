package com.pride.test4ksoft

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.pride.test4ksoft.databinding.ActivityMainBinding
import com.pride.test4ksoft.fragments.Listnote

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.place_holder, Listnote()).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return true
    }
}