package com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.userprofile.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val dateOfBirth: String,
    val district: String,
    val mobile: String
)
