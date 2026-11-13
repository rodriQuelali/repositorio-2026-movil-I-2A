package com.example.myapplicationmvvmsqlite.ui

sealed interface UiEvent {
    object Saved : UiEvent
    data class Message(val text: String) : UiEvent
}