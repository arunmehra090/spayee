package com.firebaseChat.extension

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel

fun Context.showShortToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun FragmentActivity.showShortToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun FragmentActivity.showLongToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun AndroidViewModel.showShortToast(msg: String) {
    Toast.makeText(getApplication(), msg, Toast.LENGTH_SHORT).show()
}

fun AndroidViewModel.showLongToast(msg: String) {
    Toast.makeText(getApplication(), msg, Toast.LENGTH_LONG).show()
}

fun EditText.closeKeyboard() {
    this.context?.apply {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(windowToken, 0)
        }
    }
}

fun EditText.openKeyboard() {
    this.context?.apply {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            showSoftInput(this@openKeyboard, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}

fun EditText.clearTextField() {
    this.text.clear()
}

fun View.show() { this.visibility = View.VISIBLE }

fun View.gone() { this.visibility = View.GONE }

fun ViewGroup.show() { this.visibility = View.VISIBLE }

fun ViewGroup.gone() { this.visibility = View.GONE }


