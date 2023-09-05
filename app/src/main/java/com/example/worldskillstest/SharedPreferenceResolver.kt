package com.example.worldskillstest

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri

import org.json.JSONObject

class SharedPreferenceResolver(context: Context) {
    var sharedPref = context.getSharedPreferences("Session", Context.MODE_PRIVATE)

    fun setUserSession(jsonObject: JSONObject) {
        with(sharedPref.edit()) {
            this.putString("session", jsonObject.toString())
            apply()
        }
    }
    fun getUserSession(): JSONObject? {
       return sharedPref.getString("session", JSONObject().toString())?.let { JSONObject(it) }
    }

    fun clearUserSession() {
        with(sharedPref.edit()) {
            this.remove("session")
            apply()
        }
    }

    fun setUserPfp(uri: Uri) {
        val username = getUserSession()?.getString("email")
        with(sharedPref.edit()) {
            this.putString("pfp-$username", uri.toString())
            apply()
        }

    }

    fun getUserPfp(): Uri? {
        val username = getUserSession()?.getString("email")
        val i = sharedPref.getString("pfp-$username", null)
        if(i == null) {
            return null
        } else {
            return i.toUri()
        }

    }


}