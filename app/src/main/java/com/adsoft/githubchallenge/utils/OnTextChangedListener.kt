package com.adsoft.githubchallenge.utils

import android.text.Editable
import android.text.TextWatcher

interface OnTextChangedListener: TextWatcher {
    fun onTextChangedListener(s: Editable?)

    override fun afterTextChanged(s: Editable?) { onTextChangedListener(s) }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}