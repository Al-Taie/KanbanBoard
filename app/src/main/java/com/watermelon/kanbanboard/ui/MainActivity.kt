package com.watermelon.kanbanboard.ui

import android.view.LayoutInflater
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.databinding.ActivityMainBinding
import com.watermelon.kanbanboard.ui.base.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val theme = R.style.Theme_KanbanBoard

    override fun setup() {}

    override fun callBack() {}

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate
}