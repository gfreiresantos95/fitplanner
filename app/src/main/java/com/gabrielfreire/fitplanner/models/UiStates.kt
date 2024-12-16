package com.gabrielfreire.fitplanner.models

enum class UiStates(val state: String) {
    EMPTY("empty"),
    ERROR("error"),
    INVALID("invalid"),
    LOADED("loaded"),
    LOADING("loading"),
}