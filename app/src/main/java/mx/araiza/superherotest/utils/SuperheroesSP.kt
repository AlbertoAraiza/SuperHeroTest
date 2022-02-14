package mx.araiza.superherotest.utils

import android.content.Context

class SuperheroesSP(ctx :Context) {
        private val SHARED_NAME = "superhero_shared_preferences"
        private val SHARED_SAVED = "shared_saved"
        private val preferences = ctx.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        private val editor = preferences.edit()

        fun setSaved(newSaved :Int){
            editor.putInt(SHARED_SAVED, newSaved).apply()
        }

        fun getSaved():Int = preferences.getInt(SHARED_SAVED, 0)
}