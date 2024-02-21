package com.example.realmdatabase.Data.models

import io.realm.kotlin.types.EmbeddedRealmObject

// Teacher -> Address (one to one relation)
// Teacher -> Courses (one to many relation)
// Student -> Courses (many to many relation)

// because we will directly embed to the body of the teacher object
class Address : EmbeddedRealmObject {
  //  @PrimaryKey var id : ObjectId = ObjectId() but tacher is already the unique id we have
    var fullName : String = ""
    var street : String = ""
    var zip : Int = 0
    var houseNumber : Int = 0
    var city : String = ""
    var teacher : Teacher? = null
}