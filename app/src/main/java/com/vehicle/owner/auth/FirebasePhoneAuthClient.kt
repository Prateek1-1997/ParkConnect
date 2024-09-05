package com.vehicle.owner.auth

import android.app.Activity
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.messaging.FirebaseMessaging
import java.util.concurrent.TimeUnit

class FirebasePhoneAuthClient {
    private val auth = Firebase.auth
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    init {
        handleInit()
    }

    private fun handleInit() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.e("TAG", "onVerificationCompleted:$credential")
                //signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.e("TAG", "onVerificationFailed", e)
                Firebase.crashlytics.recordException(Throwable("onVerificationFailed ${e}"))
                FirebaseCrashlytics.getInstance().recordException(Throwable("onVerificationFailed ${e}"))
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                    // reCAPTCHA verification attempted with null Activity
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                Firebase.crashlytics.recordException(Throwable("onCodeSent ${verificationId}"))
                FirebaseCrashlytics.getInstance().recordException(Throwable("onCodeSent ${verificationId}"))
                Log.e("TAG", "onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token
            }
        }
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            username = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    fun startPhoneNumberVerification(phoneNumber: String, activity: Activity) {
        Firebase.crashlytics.recordException(Throwable("startPhoneNumberVerification ${phoneNumber}"))
        FirebaseCrashlytics.getInstance().recordException(Throwable("startPhoneNumberVerification ${phoneNumber}"))
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyOtp(otp: String, onOtpSuccessful: (FirebaseUser?) -> Unit) {
        val credential = PhoneAuthProvider.getCredential(storedVerificationId.orEmpty(), otp)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onOtpSuccessful(task.result?.user)
                } else {
                    Firebase.crashlytics.recordException(Throwable("addOnCompleteListener Failure"))
                    FirebaseCrashlytics.getInstance().recordException(Throwable("addOnCompleteListener Failure"))
                    Log.e("TAG", "onCodeSent: Failure")
                }
            }.addOnFailureListener {
                Firebase.crashlytics.recordException(Throwable("addOnFailureListener ${it}"))
                FirebaseCrashlytics.getInstance().recordException(Throwable("addOnFailureListener ${it}"))
                Log.e("TAG", "onCodeSent: E ${it}")
            }
    }

    fun getFcmToken(fcmToken: (String) -> Unit) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val token = task.result
                    fcmToken(token)
                    Log.d("FCM Token", "Token: $token")
                } else {
                    Log.e("FCM Token", "Failed to get token: ${task.exception}")
                }
            }
    }


}