package com.example.worldskillstest

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Integer.parseInt
import java.net.HttpURLConnection
import java.net.URL

object WorldSkillsAPI {
    var baseUrl = "http://169.254.73.126:8000"
    suspend fun login(email: String, password: String, accessKey: Int): JSONObject{
        println(JSONObject().apply {
            put("email", email)
            put("password", password)
            put("accesskey", accessKey)
        }.toString())
        return postRequest(url = "$baseUrl/api/wss/userLogin", json = JSONObject().apply {
            put("email", email)
            put("password", password)
            put("accesskey", accessKey)
        })

    }
    suspend fun changePassword(oldPassword: String, newPassword: String, confirmNewPassword: String, accountId: String): JSONObject {
        return postRequest(url = "$baseUrl/api/wss/changePassword", json = JSONObject().apply {
            put("accountid", parseInt(accountId))
            put("oldpwd", oldPassword)
            put("newpwd", newPassword)
            put("cnfmpwd", confirmNewPassword)
            put("accesskey", 2)
        })
    }
    private suspend fun postRequest(url:String, json: JSONObject): JSONObject {

        return withContext(Dispatchers.IO) {
            try {
                val urls = URL(url)
                var response: JSONObject = JSONObject()

                with(urls.openConnection() as HttpURLConnection) {
                    requestMethod =  "POST"
                    doOutput = true
                    doInput = true
                    connectTimeout = 5000
                    readTimeout = 5000


                    setRequestProperty("Content-Type", "application/json")
                    setRequestProperty("accept", "application/json")
                    println(json.toString())
                    outputStream.write(json.toString().toByteArray())
                    if(responseCode in (200..299)) {
                        val stream = inputStream.bufferedReader().readText()
                        return@with JSONObject(stream)
                    } else {

                        val errorStream = errorStream.bufferedReader().readText()

                        return@with JSONObject(errorStream)
                    }

                }
            } catch (e: Exception) {
                println(e.message)
                return@withContext JSONObject().apply {

                    put("status", "error")
                    put("result", JSONObject().apply {
                        put("message", "Something went wrong, try again")
                    })
                }
            }

        }

    }
}



//data  class MainResponse<T>()
