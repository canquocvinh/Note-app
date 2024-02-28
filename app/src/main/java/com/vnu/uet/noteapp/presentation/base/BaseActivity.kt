package com.vnu.uet.noteapp.presentation.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.vnu.uet.noteapp.R

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initCollectData()
        initAction()
    }

    protected open fun initView() {}

    protected open fun initCollectData() {}

    protected open fun initAction() {}

    fun showToast(text: String) {
        Toast.makeText(this.applicationContext, text, Toast.LENGTH_SHORT).show()
    }
}