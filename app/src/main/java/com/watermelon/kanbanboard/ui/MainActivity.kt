package com.watermelon.kanbanboard.ui

import android.content.ContentValues
import android.icu.text.SimpleDateFormat
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.util.toAndroidXPair
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayoutMediator
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.data.TaskDbHelper
import com.watermelon.kanbanboard.databinding.ActivityMainBinding
import com.watermelon.kanbanboard.ui.base.BaseActivity
import com.watermelon.kanbanboard.ui.done.DoneFragment
import com.watermelon.kanbanboard.ui.home.HomeFragment
import com.watermelon.kanbanboard.ui.home.ViewPagerAdapter
import com.watermelon.kanbanboard.ui.in_progress.InProgressFragment
import com.watermelon.kanbanboard.ui.interfaces.CustomDialogFragment
import com.watermelon.kanbanboard.ui.todo.TodoFragment
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>(), CustomDialogFragment {
    private val databaseHelper by lazy { TaskDbHelper(applicationContext) }
    private val isLargeLayout by lazy { resources.getBoolean(R.bool.large_layout) }
    override val theme = R.style.Theme_KanbanBoard
    private var isDialog = false

    override fun setup() {
        initViewPager()
        initTabLayout()
    }

    override fun callBack() {
        addTask()
    }

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private fun initTabLayout() {
        val tabTitles = listOf("Home", "ToDo", "In Progress", "Done")
        val tabIcons = listOf(
            R.drawable.ic_home,
            R.drawable.ic_checklist,
            R.drawable.ic_in_progress,
            R.drawable.ic_done
        ).map(this::getDrawable)

        binding.apply {
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//                tab.text = tabTitles[position]
                tab.icon = tabIcons[position]
                if (position != 0)
                    tab.orCreateBadge.number = position
            }.attach()
        }
    }

    private fun addTask() {
        val newEntry = ContentValues()

        with(TaskDbHelper.DB) {
            newEntry.apply {
                put(TITLE, "TEST")
                put(DESCRIPTION, "TEST")
                put(STATUS, "TEST")
                put(DATE, "2021-09-05")
                put(EXPANDED, 0)
            }
            databaseHelper.writableDatabase.insert(TABLE_NAME, null, newEntry)
        }
    }

    private fun initViewPager() {
        val fragmentsList = listOf(
            HomeFragment(),
            TodoFragment(this),
            InProgressFragment(),
            DoneFragment()
        )
        val adapter = ViewPagerAdapter(this, fragmentsList = fragmentsList)
        binding.viewPager.adapter = adapter
    }

    override fun showDialog(fragment: DialogFragment) {
        if (isDialog)
            return

        if (isLargeLayout) {
            // The device is using a large layout, so show the fragment as a dialog
            fragment.show(supportFragmentManager, "dialog")
        } else {
            // The device is smaller, so show the fragment fullscreen
            supportFragmentManager.beginTransaction().apply {
                // For a little polish, specify a transition animation
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                // To make it fullscreen, use the 'content' root view as the container
                // for the fragment, which is always the root view for the activity

                add(android.R.id.content, fragment)
                addToBackStack(null)
                commit()
            }
        }

        componentsVisibility(false)
    }

    override fun showDatePicker() {}

    override fun onBackPressed() {
        super.onBackPressed()
        componentsVisibility(true)
    }

    private fun componentsVisibility(show: Boolean) {
        val visibility = if (show) View.VISIBLE else View.GONE
        TransitionManager.beginDelayedTransition(binding.root)
        binding.apply {
            tabLayout.visibility = visibility
            viewPager.visibility = visibility
        }
        isDialog = !show
    }

}