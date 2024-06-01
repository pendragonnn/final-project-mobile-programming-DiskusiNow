package com.dev.final_project_diskusinow.utils

import org.json.JSONException
import org.json.JSONObject

object JsonUtils {
    fun extractMessageFromJson(jsonString: String?): String? {
        return try {
            val jsonObject = JSONObject(jsonString)
            jsonObject.getString("message")
        } catch (e: JSONException) {
            null
        }
    }
}