package com.example.firebaseuserproccessing

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.firebaseuserproccessing.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=Firebase.auth
        val guncelkullanici=auth.currentUser
        if(guncelkullanici!=null){
            val intent=Intent(this,DusunceActivity::class.java)
            startActivity(intent)
        }
        binding.buttonKaydol.setOnClickListener {
            val email=binding.emailtext.text.toString().trim()
            val sifre=binding.parolatext.text.toString().trim()
            val kullanici_adi=binding.kullanicitext.text.toString().trim()
            auth.createUserWithEmailAndPassword(email,sifre)
                .addOnCompleteListener(this) {task ->
                    if(task.isSuccessful){
                        val guncelkullanici=auth.currentUser


                        val profileUpdates = userProfileChangeRequest {
                            displayName = kullanici_adi
                        }

                        guncelkullanici!!.updateProfile(profileUpdates)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(applicationContext,"${kullanici_adi} hoşgeldin",Toast.LENGTH_LONG).show()
                                }
                            }

                        Toast.makeText(applicationContext,"kayıt başarılı",Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener { exception->
                    Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                }
        }
        binding.buttonGiris.setOnClickListener {
            val email=binding.emailtext.text.toString().trim()
            val sifre=binding.parolatext.text.toString().trim()
            auth.signInWithEmailAndPassword(email,sifre)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext,"${auth.currentUser?.displayName.toString()} hoşgeldiniz",Toast.LENGTH_LONG).show()
                        val intent=Intent(this,DusunceActivity::class.java)
                        startActivity(intent)
                    }

                }.addOnFailureListener{exception -> Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()}
        }

    }

}