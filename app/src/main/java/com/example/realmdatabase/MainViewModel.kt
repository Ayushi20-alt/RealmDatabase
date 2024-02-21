package com.example.realmdatabase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.realmdatabase.Data.models.Address
import com.example.realmdatabase.Data.models.Course
import com.example.realmdatabase.Data.models.Student
import com.example.realmdatabase.Data.models.Teacher
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
     private val realm = MyApp.realm

    // ui cant work with realmQuery<Courses> so it must be like a flow to be ablw to observe chnange and work with ui
    // so this is query and with room we can react to changes in database
    // we listen to the queries as flow and map into these normal list and saving as stateflow
     val courses = realm.query<Course>(
         "enrolledStrudents.name == $0", "John Junior"
     ).asFlow().map { results ->
         results.list.toList()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList()) // to use it as a state

    init {
        createSampleEntries()
    }

     private fun createSampleEntries(){
          viewModelScope.launch {
               // all database actions we perform in this mutable realm is known as transaction
               // and will be excuted when only single ight transactions only succeded
               // or it will not save part of query so it will revert the data
              realm.write {
                 val address1 = Address().apply {
                     fullName = "John Doe"
                     street = "John Doe street"
                     houseNumber = 24
                     zip = 12345
                     city = "orai"
                 }
                  val address2 = Address().apply {
                      fullName = "Jane Doe"
                      street = "Jane Doe street"
                      houseNumber = 25
                      zip = 12346
                      city = "orai"
                  }
                  val course1 = Course().apply {
                      name = "Kotlin programming made easy"
                  }
                  val course2 = Course().apply {
                      name = "Android Basics"
                  }
                  val course3 = Course().apply {
                      name = "Asynchronous Programming With Coroutines"
                  }
                  val teacher1 = Teacher().apply {
                      address = address1
                      courses = realmListOf(course1, course2)
                  }
                  val teacher2 = Teacher().apply {
                      address = address2
                      courses = realmListOf(course3)
                  }
                  val student1 = Student().apply {
                      name = "John Junior"
                  }
                  val student2 = Student().apply {
                      name = "Jane Junior"
                  }

                  course1.teacher = teacher1
                  course2.teacher = teacher1
                  course3.teacher = teacher2

                  address1.teacher = teacher1
                  address2.teacher = teacher2

                  course1.enrolledStrudents.add(student1)
                  course2.enrolledStrudents.add(student2)
                  course3.enrolledStrudents.addAll(listOf(student1,student2))

                  copyToRealm(teacher1, updatePolicy = UpdatePolicy.ALL)
                  copyToRealm(teacher2, updatePolicy = UpdatePolicy.ALL)

                  copyToRealm(course1, updatePolicy = UpdatePolicy.ALL)
                  copyToRealm(course2, updatePolicy = UpdatePolicy.ALL)
                  copyToRealm(course3, updatePolicy = UpdatePolicy.ALL)

                  copyToRealm(student1, updatePolicy = UpdatePolicy.ALL)
                  copyToRealm(student2, updatePolicy = UpdatePolicy.ALL)
              }
          }
     }
}