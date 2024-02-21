package com.example.realmdatabase

import android.app.Application
import com.example.realmdatabase.Data.models.Address
import com.example.realmdatabase.Data.models.Course
import com.example.realmdatabase.Data.models.Student
import com.example.realmdatabase.Data.models.Teacher
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class MyApp:Application() {
    companion object{
        lateinit var realm : Realm
    }

    override fun onCreate() {
        super.onCreate()
        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    Address::class,
                    Student::class,
                    Teacher::class,
                    Course::class
                )
            )
        )
    }
}