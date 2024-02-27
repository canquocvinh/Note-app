package com.vnu.uet.noteapp.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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
}