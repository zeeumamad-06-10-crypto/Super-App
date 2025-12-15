package com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.userprofile.database

import androidx.lifecycle.LiveData


class UserProfileRepository(private val dao: UserProfileDao) {

    val allProfiles: LiveData<List<UserProfile>> = dao.getAllProfiles()
    val profileCount: LiveData<Int> = dao.getProfileCount()

    suspend fun insert(profile: UserProfile) = dao.insertProfile(profile)
    suspend fun update(profile: UserProfile) = dao.updateProfile(profile)
    suspend fun delete(profile: UserProfile) = dao.deleteProfile(profile)
    fun getProfileById(id: Int) = dao.getProfileById(id)
}
