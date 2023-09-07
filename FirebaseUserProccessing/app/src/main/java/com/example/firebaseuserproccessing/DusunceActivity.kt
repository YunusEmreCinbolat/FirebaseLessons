package com.example.firebaseuserproccessing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.firebaseuserproccessing.databinding.ActivityDusunceBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DusunceActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivityDusunceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDusunceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=Firebase.auth

        binding.buttonCikis.setOnClickListener {
            auth.signOut()
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}