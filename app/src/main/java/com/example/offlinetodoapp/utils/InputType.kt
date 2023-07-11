package com.example.offlinetodoapp.utils

sealed interface InputType {
    object TaskTitle : InputType

    object TaskDescription : InputType
}