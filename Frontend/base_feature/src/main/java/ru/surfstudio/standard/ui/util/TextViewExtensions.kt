package ru.surfstudio.standard.ui.util

import com.google.android.material.textfield.TextInputEditText

var TextInputEditText.distinctText: CharSequence
    get() = text.toString()
    set(value) {
        if (text == value) return
        setText(value)
    }