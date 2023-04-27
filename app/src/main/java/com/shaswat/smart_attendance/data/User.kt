package com.shaswat.smart_attendance.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    @Expose
    var name: String = "name",
    @SerializedName("email")
    @Expose
    var email: String = "",
    @SerializedName("picture")
    @Expose
    var picture: String = "",
    @SerializedName("roll")
    @Expose
    var roll: String = "",
    @SerializedName("sub")
    @Expose
    var sub: String = "",
    @SerializedName("given_name")
    @Expose
    var given_name: String = "",
    @SerializedName("family_name")
    @Expose
    val family_name: String = "",
    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber: String = "",
    @SerializedName("batch")
    @Expose
    val batch: String = "",
    @SerializedName("department")
    @Expose
    val department: String = "",
    @SerializedName("faceData")
    @Expose
    val faceData: Boolean,
)
