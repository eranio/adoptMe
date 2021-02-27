package com.example.androiddevchallenge

import android.os.Parcel
import android.os.Parcelable

data class Pet(private val id: Int ,private val name: String?, private val age: Int, private val imageId:Int): IPet,
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun getId(): Int {
        return id
    }

    override fun getName(): String {
        return name?: ""
    }

    override fun getAge(): Int {
        return age
    }

    override fun getImageId(): Int {
        return imageId
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeInt(imageId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pet> {
        override fun createFromParcel(parcel: Parcel): Pet {
            return Pet(parcel)
        }

        override fun newArray(size: Int): Array<Pet?> {
            return arrayOfNulls(size)
        }
    }
}