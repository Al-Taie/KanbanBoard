package com.watermelon.kanbanboard.ui

import android.content.ContentValues
import android.view.LayoutInflater
import com.google.android.material.tabs.TabLayoutMediator
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.data.TaskDbHelper
import com.watermelon.kanbanboard.databinding.ActivityMainBinding
import com.watermelon.kanbanboard.ui.adapter.ItemAdapter
import com.watermelon.kanbanboard.ui.add.AddFragment
import com.watermelon.kanbanboard.ui.base.BaseActivity
import com.watermelon.kanbanboard.ui.done.DoneFragment
import com.watermelon.kanbanboard.ui.home.HomeFragment
import com.watermelon.kanbanboard.ui.home.ViewPagerAdapter
import com.watermelon.kanbanboard.ui.in_progress.InProgressFragment
import com.watermelon.kanbanboard.ui.todo.TodoFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var databaseHelper: TaskDbHelper
    override val theme = R.style.Theme_KanbanBoard

    override fun setup() {
        databaseHelper = TaskDbHelper(applicationContext)
    }

    override fun callBack() {
        addTask()
    }
    override fun setup() {
        initViewPager()
        initTabLayout()
    }

    private fun initTabLayout() {
        val tabTitles = listOf("Home", "ToDo", "In Progress", "Done")
        val tabIcons = listOf(
            R.drawable.ic_home,
            R.drawable.ic_checklist,
            R.drawable.ic_in_progress,
            R.drawable.ic_done,
            R.drawable.ic_done
        ).map(this::getDrawable)

    private fun addTask() {
        val newEntry = ContentValues()

        with(TaskDbHelper.DB) {
            newEntry.apply {
                put(TITLE, "TEST")
                put(DESCRIPTION, "TEST")
                put(STATUS, "TEST")
                put(DATE, "2021-09-05")
            }
            databaseHelper.writableDatabase.insert(TABLE_NAME, null, newEntry)
        }
    }
        binding.apply {
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//                tab.text = tabTitles[position]
                tab.icon = tabIcons[position]
                tab.orCreateBadge.number = position
            }.attach()
        }
    }

    override fun callBack() {
    }

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private fun initViewPager() {
        val fragmentsList = listOf(
            HomeFragment(),
            TodoFragment(),
            InProgressFragment(),
            DoneFragment(),
            AddFragment()
        )
        val adapter = ViewPagerAdapter(this, fragmentsList = fragmentsList)
        binding.viewPager.adapter = adapter
    }
}