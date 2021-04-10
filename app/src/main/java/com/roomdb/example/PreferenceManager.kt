package com.roomdb.example

import android.content.Context
import android.content.SharedPreferences

open class PreferenceManager constructor(context: Context) : IPreferenceHelper {
    private val PREFS_NAME = "SharedPreferenceDemo"
    private var preferences: SharedPreferences
    init {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    override fun setApiKey(apiKey: String) {
        preferences[DIST_VALUE] = apiKey
    }
    override fun getApiKey(): String {
        return preferences[DIST_VALUE] ?: ""
    }


    override fun setStartTimer(startTime: String) {
        preferences[START_TIMER] = startTime
    }
    override fun getStartTimer(): String {
        return preferences[START_TIMER] ?: ""
    }

    override fun setDivision(division: String) {
        preferences[DIVISION] = division
    }
    override fun getDivision(): String {
        return preferences[DIVISION] ?: ""
    }


    override fun setSerialNo(slNo: String) {
        preferences[SL_NO] = slNo
    }
    override fun getSerialNo(): String {
        return preferences[SL_NO] ?: ""
    }

    override fun setDistrict(district: String) {
        preferences[DISTRICT] = district
    }
    override fun getDistrict(): String {
        return preferences[DISTRICT] ?: ""
    }

    override fun setEndTimer(endTime: String) {
        preferences[END_TIMER] = endTime
    }
    override fun getEndTimer(): String {
        return preferences[END_TIMER] ?: ""
    }

    override fun setUserId(userId: String) {
        preferences[SITE_VALUE] = userId
    }
    override fun getUserId(): String {
        return preferences[SITE_VALUE] ?: ""
    }
    override  fun clearPrefs() {
        preferences.edit().clear().apply()
    }
    companion object {
        const val DIST_VALUE = "dist_value"
        const val SITE_VALUE = "site_value"
        const val START_TIMER="start_timer"
        const val END_TIMER="end_timer"
        const val SL_NO="serial_no"
        const val DIVISION="division"
        const val DISTRICT="district"
    }
}
/**
 * SharedPreferences extension function, to listen the edit() and apply() fun calls
 * on every SharedPreferences operation.
 */
private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    operation(editor)
    editor.apply()
}
/**
 * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
 */
private operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}
/**
 * finds value on given key.
 * [T] is the type of value
 * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
 */
private inline operator fun <reified T : Any> SharedPreferences.get(
    key: String,
    defaultValue: T? = null
): T? {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T?
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
        else -> throw UnsupportedOperationException("Not yet implemented")
    }
}