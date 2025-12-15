package com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.userprofile.database


import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface UserProfileDao {

    @Insert
    suspend fun insertProfile(profile: UserProfile)

    @Update
    suspend fun updateProfile(profile: UserProfile)

    @Delete
    suspend fun deleteProfile(profile: UserProfile)

    @Query("SELECT * FROM user_profiles ORDER BY id DESC")
    fun getAllProfiles(): LiveData<List<UserProfile>>

    @Query("SELECT * FROM user_profiles WHERE id = :id")
    fun getProfileById(id: Int): LiveData<UserProfile>

    @Query("SELECT COUNT(*) FROM user_profiles")
    fun getProfileCount(): LiveData<Int>
}



