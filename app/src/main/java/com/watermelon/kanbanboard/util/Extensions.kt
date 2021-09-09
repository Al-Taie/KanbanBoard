package com.watermelon.kanbanboard.util

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.watermelon.kanbanboard.data.domain.Task

/**
 * @param index Integer or Null
 * @param task Task Object
 * @author     Ahmed Mones
 * @return     Unit
 * */
fun MutableList<Task>.add(index: Int?, task: Task) {
    if (index == null) {
        this.add(task)
    } else {
        this.add(index, task)
    }
}


/**
 * Get last id from list of tasks
 * @author     Ahmed Mones
 * @return     Unit
 * */
fun List<Task>.lastId(): Int {
    return try {
        this.last().id + 1
    } catch (e: Exception) {
        1
    }
}

/**
 * Make slide animation on change view visibility
 * @author     Ahmed Mones
 * @see        <a href="https://stackoverflow.com/a/67856091/16375959">Stakoverfolw Answer</a>
 * @return     Unit
 * */
fun View.slideVisibility(visibility: Boolean, duration: Long = 350, gravity: Int = Gravity.BOTTOM) {
    val transition = Slide(gravity)
    transition.apply {
        this.duration = duration
        addTarget(this@slideVisibility)
    }
    TransitionManager.beginDelayedTransition(parent as ViewGroup, transition)
    isVisible = visibility
}
