package com.refanda.restoran.data.source.local.pref

import android.content.Context
import com.refanda.restoran.utils.SharedPreferenceUtils
import com.refanda.restoran.utils.SharedPreferenceUtils.set

interface UserPreference {
    fun isUsingGridMode(): Boolean
    fun setUsingGridMode(isUsingDarkMode: Boolean)
}

class UserPreferenceImpl(private val context: Context) : UserPreference{

    private val pref = SharedPreferenceUtils.createPreference(context, PREF_NAME)

    override fun isUsingGridMode(): Boolean = pref.getBoolean(KEY_IS_USING_GRID_MODE, false)

    override fun setUsingGridMode(isUsingDarkMode: Boolean) {
        pref[KEY_IS_USING_GRID_MODE] = isUsingDarkMode
    }

    companion object {
        const val PREF_NAME = "restoran-pref"
        const val KEY_IS_USING_GRID_MODE = "KEY_IS_USING_GRID_MODE"
    }
}