package com.watermelon.kanbanboard.ui

import android.view.LayoutInflater
import com.google.android.material.tabs.TabLayoutMediator
import com.watermelon.kanbanboard.R
import com.watermelon.kanbanboard.databinding.ActivityMainBinding
import com.watermelon.kanbanboard.ui.base.BaseActivity
import com.watermelon.kanbanboard.ui.home.HomeFragment
import com.watermelon.kanbanboard.ui.home.ViewPagerAdapter
import com.watermelon.kanbanboard.ui.todo.TodoFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val theme = R.style.Theme_KanbanBoard

    override fun setup() {
        initViewPager()
        initTabLayout()
    }

    private fun initTabLayout() {
        val tabTitles = listOf("Home", "ToDo", "In Progress", "Done")
        val tabIcons = listOf(
            R.drawable.ic_home,
            R.drawable.ic_in_progress,
            R.drawable.ic_done
        ).map(this::getDrawable)

        binding.apply {
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//                tab.text = tabTitles[position]
                tab.icon = tabIcons[position]
                tab.orCreateBadge.number = position
            }.attach()
        }
    }

    override fun callBack() {}

    override val inflate: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private fun initViewPager() {
        val fragmentsList = listOf(HomeFragment(), TodoFragment())
        val adapter = ViewPagerAdapter(this, fragmentsList = fragmentsList)
        binding.viewPager.adapter = adapter
    }
}