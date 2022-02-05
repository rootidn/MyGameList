package com.example.mygamelist.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Games (
    val id : Int,
    val slug : String,
    val name : String,
    val released : String,
    val background_image : String,
    val rating : Float,
    val genres : ArrayList<Genres>
) : Parcelable