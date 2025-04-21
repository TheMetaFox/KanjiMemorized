package com.example.kanjimemorized

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase

object FirebaseModule {
    var firebaseAnalytics: FirebaseAnalytics = Firebase.analytics
}