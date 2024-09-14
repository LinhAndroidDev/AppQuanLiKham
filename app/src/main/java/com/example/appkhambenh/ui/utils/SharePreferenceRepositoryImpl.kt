package com.example.appkhambenh.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.AUTHORIZATION
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.CHECK_LOGIN
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.DATE_APPOINTMENT
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.EMAIL
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.HOUR_APPOINTMENT
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.IDENTIFICATION
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.ID_DAY
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.INDEX_ETHNICS
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.INDEX_JOB
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.INDEX_NATIONALITY
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.LANGUAGE
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.PASSWORD
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.REMEMBER_PASSWORD
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.ROLL_USER
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.USER_ADDRESS
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.USER_AVATAR
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.USER_BIRTH
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.USER_EMAIL
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.USER_ID
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.USER_NAME
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.USER_PHONE
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.USER_SEX
import com.example.appkhambenh.ui.utils.SharePreferenceRepository.Companion.USER_TYPE
import java.io.Serializable

class SharePreferenceRepositoryImpl(val ctx: Context): SharePreferenceRepository,
    PreferenceUtil(ctx) {

    private val prefs by lazy { defaultPref() }

    override fun saveAuthorization(authorization: String) {
        prefs[AUTHORIZATION] = authorization
    }

    override fun getAuthorization(): String = prefs[AUTHORIZATION] ?: ""

    override fun clearAuthorization() {
        prefs.edit().remove(AUTHORIZATION).apply()
    }

    override fun saveUserId(id: Int) {
        prefs[USER_ID] = id
    }

    override fun getUserId(): Int = prefs[USER_ID] ?: -1

    override fun saveUserName(name: String) {
        prefs[USER_NAME] = name
    }

    override fun getUserName(): String = prefs[USER_NAME] ?: ""

    override fun saveUserBirth(birth: String) {
        prefs[USER_BIRTH] = birth
    }

    override fun getUserBirth(): String = prefs[USER_BIRTH] ?: ""

    override fun saveUserAvatar(avatar: String) {
        prefs[USER_AVATAR] = avatar
    }

    override fun getUserAvatar(): String = prefs[USER_AVATAR] ?: ""

    override fun saveUserPhone(phone: String) {
        prefs[USER_PHONE] = phone
    }

    override fun getUserPhone(): String = prefs[USER_PHONE] ?: ""

    override fun saveUserAddress(address: String) {
        prefs[USER_ADDRESS] = address
    }

    override fun getUserAddress(): String = prefs[USER_ADDRESS] ?: ""

    override fun saveUserEmail(email: String) {
        prefs[USER_EMAIL] = email
    }

    override fun getUserEmail(): String = prefs[USER_EMAIL] ?: ""

    override fun saveUserSex(sex: Int) {
        prefs[USER_SEX] = sex
    }

    override fun getUserSex(): Int = prefs[USER_SEX] ?: -1

    override fun saveUserType(type: Int) {
        prefs[USER_TYPE] = type
    }

    override fun getUserType(): Int = prefs[USER_TYPE] ?: -1

    override fun saveRememberPassword(remember: Boolean) {
        prefs[REMEMBER_PASSWORD] = remember
    }

    override fun getRememberPassword(): Boolean = prefs[REMEMBER_PASSWORD] ?: false

    override fun saveEmail(email: String) {
        prefs[EMAIL] = email
    }

    override fun getEmail(): String = prefs[EMAIL] ?: ""

    override fun savePassword(password: String) {
        prefs[PASSWORD] = password
    }

    override fun getPassword(): String = prefs[PASSWORD] ?: ""

    override fun saveCheckLogin(isLogin: Boolean) {
        prefs[CHECK_LOGIN] = isLogin
    }

    override fun getCheckLogin(): Boolean = prefs[CHECK_LOGIN] ?: false

    override fun saveDateAppoint(date: String) {
        prefs[DATE_APPOINTMENT] = date
    }

    override fun getDateAppoint(): String = prefs[DATE_APPOINTMENT] ?: ""

    override fun saveHourAppoint(hour: String) {
        prefs[HOUR_APPOINTMENT] = hour
    }

    override fun getHourAppoint(): String = prefs[HOUR_APPOINTMENT] ?: ""

    override fun saveIdDay(id: Int) {
        prefs[ID_DAY] = id
    }

    override fun getIdDay(): Int = prefs[ID_DAY] ?: 0

    override fun saveLanguage(language: String) {
        prefs[LANGUAGE] = language
    }

    override fun getLanguage(): String = prefs[LANGUAGE] ?: "vi"

    override fun saveIndexEthnics(ethnics: Int) {
        prefs[INDEX_ETHNICS] = ethnics
    }

    override fun getIndexEthnics(): Int = prefs[INDEX_ETHNICS] ?: 0

    override fun saveIndexNationality(nationality: Int) {
        prefs[INDEX_NATIONALITY] = nationality
    }

    override fun getIndexNationality(): Int = prefs[INDEX_NATIONALITY] ?: 0

    override fun saveIndexJob(job: Int) {
        prefs[INDEX_JOB] = job
    }

    override fun getIndexJob(): Int = prefs[INDEX_JOB] ?: 0

    override fun saveIdentification(identification: String) {
        prefs[IDENTIFICATION] = identification
    }

    override fun getIdentification(): String = prefs[IDENTIFICATION] ?: ""

    override fun saveRollUser(rollId: Int) {
        prefs[ROLL_USER] = rollId
    }

    override fun getRollUser(): Int = prefs[ROLL_USER] ?: 1
}