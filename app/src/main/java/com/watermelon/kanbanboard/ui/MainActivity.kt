package com.watermelon.kanbanboard.ui

import android.icu.text.SimpleDateFormat
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.util.toAndroidXPair
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayoutMediator
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.data.DataManager
import com.watermelon.kanbanboard.data.database.TaskDbHelper
import com.watermelon.kanbanboard.data.domain.Task
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
    private lateinit var dbHelper: TaskDbHelper
    private val isLargeLayout by lazy { resources.getBoolean(R.bool.large_layout) }
    override val theme = R.style.Theme_KanbanBoard
    private var isDialog = false

    override fun setup() {
        initViewPager()
        initTabLayout()
        dbHelper = TaskDbHelper(applicationContext)
       TaskDbHelper.TABLES.list.map { dbHelper.read(it) }
    }

    override fun callBack() {}

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onDestroy() {
//        TaskDbHelper.TABLES.list.map {
//            dbHelper.readableDatabase.delete(it, null, null)
//        }
        DataManager.todoList.map { dbHelper.write(it) }
        DataManager.inProgressList.map { dbHelper.write(it) }
        DataManager.doneList.map { dbHelper.write(it) }
        super.onDestroy()
    }

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

    override fun showDatePicker() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Date Picker")
            .setSelection(
                Pair(
                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds()
                ).toAndroidXPair()
            ).build()

        dateRangePicker.addOnPositiveButtonClickListener {
            findViewById<TextView>(R.id.date_viewer).text = dateFormat.format(it.first)
        }

        dateRangePicker.show(supportFragmentManager, "Date Picker")
    }

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

    override fun closeDialog(dialogFragment: DialogFragment) {
        dialogFragment.dismiss()
        componentsVisibility(true)
    }
}