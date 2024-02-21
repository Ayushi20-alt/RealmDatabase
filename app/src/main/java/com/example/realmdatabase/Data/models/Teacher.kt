package com.example.realmdatabase.Data.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

// because we don't want to embed teacher anywhere
class Teacher : RealmObject {
    @PrimaryKey var id : ObjectId = ObjectId() // newly generated id by default
    var address : Address? = null
//    var courses : List<Courses> = emptyList() we will not use this because using this realm does not have info how to link courses and teacher
    var courses : RealmList<Course> = realmListOf( )
}