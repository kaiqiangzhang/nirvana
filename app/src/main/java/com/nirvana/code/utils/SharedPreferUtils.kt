package com.nirvana.code.utils

import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.nirvana.code.NivanaApplication
import java.util.*

class SharedPreferUtils {

    companion object{
        var sharedPreference: SharedPreferences? = null
        val fileName:String = "lovein_log"
        val keyGenParamsSpec: KeyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias:String = MasterKeys.getOrCreate(keyGenParamsSpec)

        open fun getSharedInstance(): SharedPreferences? {
            synchronized(this) {
                if (sharedPreference == null) {
                    sharedPreference = EncryptedSharedPreferences.create(
                        "lovein_log",
                        masterKeyAlias,
                        NivanaApplication.mAPP,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    )
                }
            }
            return sharedPreference
        }

        open fun saveKeyValue(key:String, value:String){
            try {
                getSharedInstance()?.edit()?.putString(key,value)?.apply()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        open fun getKeyValue(key:String): String? {
            return getSharedInstance()?.getString(key,"");
        }
    }



}