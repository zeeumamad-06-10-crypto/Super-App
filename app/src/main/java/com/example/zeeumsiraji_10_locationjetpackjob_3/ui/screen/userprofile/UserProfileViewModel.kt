//package com.example.zeeumsiraji_10_locationjetpackjob_3.ui.screen.userprofile
//
//import android.app.Application
//import androidx.lifecycle.*
//import com.example.zeeumsiraji_10_userprofileregistrationapplication.Database.AppDatabase
//import com.example.zeeumsiraji_10_userprofileregistrationapplication.Database.UserProfile
//import com.example.zeeumsiraji_10_userprofileregistrationapplication.Database.UserProfileRepository
//
//import kotlinx.coroutines.launch
//
//class UserProfileViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val repository: UserProfileRepository
//
//    val allProfiles: LiveData<List<UserProfile>>
//    val profileCount: LiveData<Int>
//
//    init {
//        val dao = AppDatabase.getDatabase(application).userProfileDao()
//        repository = UserProfileRepository(dao)
//        allProfiles = repository.allProfiles
//        profileCount = repository.profileCount
//    }
//
//    fun insert(profile: UserProfile) = viewModelScope.launch {
//        repository.insert(profile)
//    }
//
//    fun update(profile: UserProfile) = viewModelScope.launch {
//        repository.update(profile)
//    }
//
//    fun delete(profile: UserProfile) = viewModelScope.launch {
//        repository.delete(profile)
//    }
//
//    fun getProfileById(id: Int): LiveData<UserProfile> = repository.getProfileById(id)
//}
