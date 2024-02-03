package com.example.appkhambenh.ui.utils

interface SharePreferenceRepository {
    companion object {
        const val AUTHORIZATION = "AUTHORIZATION"
        const val USER_ID = "USER_ID"
        const val USER_NAME = "USER_NAME"
        const val USER_BIRTH = "USER_BIRTH"
        const val USER_AVATAR = "USER_AVATAR"
        const val USER_PHONE = "USER_PHONE"
        const val USER_ADDRESS = "USER_ADDRESS"
        const val USER_EMAIL = "USER_EMAIL"
        const val USER_SEX = "USER_SEX"
        const val USER_TYPE = "USER_TYPE"
        const val REMEMBER_PASSWORD = "REMEMBER_PASSWORD"
        const val EMAIL = "EMAIL"
        const val PASSWORD = "PASSWORD"
        const val CHECK_LOGIN = "CHECK_LOGIN"
        const val DATE_APPOINTMENT = "DATE_APPOINTMENT"
        const val HOUR_APPOINTMENT = "HOUR_APPOINTMENT"
        const val ID_DAY = "ID_DAY"
        const val LANGUAGE = "LANGUAGE"
        const val INDEX_ETHNICS = "INDEX_ETHNICS"
        const val INDEX_NATIONALITY = "INDEX_NATIONALITY"
        const val INDEX_JOB = "INDEX_JOB"
    }

    fun saveAuthorization(authorization: String)

    fun getAuthorization(): String

    fun saveUserId(id: Int)

    fun getUserId(): Int

    fun saveUserName(name: String)

    fun getUserName(): String

    fun saveUserBirth(birth: String)

    fun getUserBirth(): String

    fun saveUserAvatar(avatar: String)

    fun getUserAvatar(): String

    fun saveUserPhone(phone: String)

    fun getUserPhone(): String

    fun saveUserAddress(address: String)

    fun getUserAddress(): String

    fun saveUserEmail(email: String)

    fun getUserEmail(): String

    fun saveUserSex(sex: Int)

    fun getUserSex(): Int

    fun saveUserType(type: Int)

    fun getUserType(): Int

    fun saveRememberPassword(remember: Boolean)

    fun getRememberPassword(): Boolean

    fun saveEmail(email: String)

    fun getEmail(): String

    fun savePassword(password: String)

    fun getPassword(): String

    fun saveCheckLogin(isLogin: Boolean)

    fun getCheckLogin(): Boolean

    fun saveDateAppoint(date: String)

    fun getDateAppoint(): String

    fun saveHourAppoint(hour: String)

    fun getHourAppoint(): String

    fun saveIdDay(id: Int)

    fun getIdDay(): Int

    fun saveLanguage(language: String)

    fun getLanguage(): String

    fun saveIndexEthnics(ethnics: Int)

    fun getIndexEthnics(): Int

    fun saveIndexNationality(nationality: Int)

    fun getIndexNationality(): Int

    fun saveIndexJob(job: Int)

    fun getIndexJob(): Int
}