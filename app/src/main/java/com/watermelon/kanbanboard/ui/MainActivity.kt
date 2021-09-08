package com.watermelon.kanbanboard.ui

import android.annotation.SuppressLint
import android.graphics.drawable.InsetDrawable
import android.icu.text.SimpleDateFormat
import android.transition.TransitionManager
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.PopupMenu
import androidx.core.util.toAndroidXPair
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayoutMediator
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.data.DataManager
import com.watermelon.kanbanboard.data.database.TaskDbHelper
import com.watermelon.kanbanboard.databinding.ActivityMainBinding
import com.watermelon.kanbanboard.ui.base.BaseActivity
import com.watermelon.kanbanboard.ui.done.DoneFragment
import com.watermelon.kanbanboard.ui.home.HomeFragment
import com.watermelon.kanbanboard.ui.home.ViewPagerAdapter
import com.watermelon.kanbanboard.ui.in_progress.InProgressFragment
import com.watermelon.kanbanboard.ui.interfaces.CustomDialogFragment
import com.watermelon.kanbanboard.ui.interfaces.UpdateTabLayout
import com.watermelon.kanbanboard.ui.todo.TodoFragment
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>(), CustomDialogFragment, UpdateTabLayout {
    private lateinit var dbHelper: TaskDbHelper
    private val isLargeLayout by lazy { resources.getBoolean(R.bool.large_layout) }
    override val theme = R.style.Theme_KanbanBoard
    private var isDialog = false

    override fun setup() {
        dbHelper = TaskDbHelper(applicationContext)
        TaskDbHelper.TABLES.list.map { dbHelper.read(it) }
        initViewPager()
        initTabLayout()
    }

    override fun callBack() {}

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate


    private fun initTabLayout() {
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
                if (position != 0 && position != 3) {
                    val value = when (position) {
                        1 -> DataManager.todoList.size
                        else -> DataManager.inProgressList.size
                    }
                    if (value != 0) {
                        tab.orCreateBadge.number = value
                    } else {
                        tab.removeBadge()
                    }
                }
            }.attach()
        }
    }

    private fun initViewPager() {
        val fragmentsList = listOf(
            HomeFragment(),
            TodoFragment(this, this),
            InProgressFragment(this, this),
            DoneFragment(this, this)
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

    override fun update() {
        initTabLayout()
    }

    override fun showDialog(view: View) {
        showSpinnerMenu(view, R.menu.popup_menu)
    }

    //In the showMenu function from the previous example:
    @SuppressLint("RestrictedApi")
    private fun showSpinnerMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(this, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        if (popup.menu is MenuBuilder) {
            val menuBuilder = popup.menu as MenuBuilder
            menuBuilder.setOptionalIconsVisible(true)
            for (item in menuBuilder.visibleItems) {
                val iconMarginPx =
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 1f, resources.displayMetrics
                    )
                        .toInt()
                item.icon =
                    object : InsetDrawable(item.icon, iconMarginPx, 0, iconMarginPx, 0) {
                        override fun getIntrinsicWidth(): Int {
                            return intrinsicHeight + iconMarginPx + iconMarginPx
                        }


                    }
            }
        }

        popup.show()
    }
}