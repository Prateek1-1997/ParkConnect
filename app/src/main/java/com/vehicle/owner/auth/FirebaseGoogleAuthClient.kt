package com.vehicle.owner.auth

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.crashlytics.ktx.crashlytics

class FirebaseGoogleAuthClient(private val activity: Activity) {
    //private val auth = Firebase.auth
    private var googleSignInClient: GoogleSignInClient? = null
    private var auth: FirebaseAuth? = null
    var user = auth?.currentUser

    //private var storedVerificationId: String? = ""
    //private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    //private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    companion object {
        //private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    init {
        handleInit()
    }

    private fun handleInit() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("")
            .requestEmail()
            .build()
        Log.e("TAG", "handleInit: triggered")
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
        auth = Firebase.auth
    }

    fun handleOnActivityResult(data: Intent?, onSignInSuccessful: (FirebaseUser?) -> Unit) {
        Log.e("TAG", "handleOnActivityResult: Test")
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = task.getResult(ApiException::class.java)!!
            Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
            Log.e("TAG", "handleOnActivityResult: ${account}")
            Log.e("TAG", "handleOnActivityResult: ${account.idToken}")

            Log.e("TAG", "handleOnActivityResult: ${account.account}")

            Log.e("TAG", "handleOnActivityResult: ${account.displayName}")

            Log.e("TAG", "handleOnActivityResult: ${account.email}")
            Log.e("TAG", "handleOnActivityResult: ${account.photoUrl}")
            firebaseAuthWithGoogle(account.idToken!!,onSignInSuccessful)
            com.google.firebase.ktx.Firebase.crashlytics.recordException(Exception("handleOnActivityResult: sign-in successfully ${account}"))
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately
            Log.e("TAG", "Google sign in failed", e)
            com.google.firebase.ktx.Firebase.crashlytics.recordException(Exception("handleOnActivityResult: sign-in failed ${e}"))
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String, onSignInSuccessful: (FirebaseUser?) -> Unit) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    user = auth?.currentUser
                    onSignInSuccessful.invoke(auth?.currentUser)
                    Log.e("TAG", "firebaseAuthWithGoogle: ${user}")
                    com.google.firebase.ktx.Firebase.crashlytics.recordException(Exception("firebaseAuthWithGoogle: sign-in successfully ${user}"))
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.e("TAG", "signInWithCredential:failure", task.exception)
                    com.google.firebase.ktx.Firebase.crashlytics.recordException(Exception("firebaseAuthWithGoogle: Failed ${task.exception}"))
                    //updateUI(null)
                }
            }
    }
    // [END auth_with_google]

    // [START signin]
    fun signIn() = googleSignInClient?.signInIntent

}