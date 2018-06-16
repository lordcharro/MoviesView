package com.charroapps.moviesview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseUser
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var account: GoogleSignInAccount
    lateinit var task: Task<GoogleSignInAccount>
    lateinit var credential: AuthCredential
    lateinit var gso : GoogleSignInOptions
    private val RC_SIGN_IN = 9001
    lateinit var mAuth: FirebaseAuth

    override fun onStart() {
        super.onStart()

        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        updateUI(currentUser)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Set a listener for the login Button
        sign_in_button.setOnClickListener(this@LoginActivity)


        mAuth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent): Unit {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

        }
        task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            // Google Sign In was successful, authenticate with Firebase
            account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Toast.makeText(this@LoginActivity, "Google sign in failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun signIn() {
        val signInIntent = mGoogleSignInClient.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success
                        val user = mAuth!!.currentUser
                        updateUI(user)
                    } else {
                        // Sign in fails
                        updateUI(null)
                    }
                }
    }

    fun updateUI(user : FirebaseUser?) {

        if(user!=null){
            startActivity(Intent(this@LoginActivity,MovieList::class.java))
            finish()
        }else{

        }
    }

    override fun onClick(v: View?) {
        val i = v!!.id

        when (i) {
            R.id.sign_in_button -> signIn()
        }
    }
}