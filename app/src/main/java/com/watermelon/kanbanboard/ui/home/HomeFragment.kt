package com.watermelon.kanbanboard.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.fragment.app.Fragment
import com.razerdp.widget.animatedpieview.AnimatedPieView
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig
import com.razerdp.widget.animatedpieview.data.SimplePieInfo
import com.watermelon.kanbanboard.databinding.FragmentHomeBinding
import com.watermelon.kanbanboard.ui.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun setup() {}

    override fun callBack() {
        val chart = binding.homePieChart
        initPieChart(chart)
    }

    private fun initPieChart(chart: AnimatedPieView) {
        val config = AnimatedPieViewConfig()
        config.apply {
            startAngle(-40f)
            duration(1800)
            drawText(true)
            canTouch(true)
            textSize(36F)
            textMargin(3)
            splitAngle(1.5F)
            interpolator(DecelerateInterpolator())
            autoSize(false)
            pieRadiusRatio(1F)
            animOnTouch(true)
            focusAlphaType(AnimatedPieViewConfig.FOCUS_WITH_ALPHA_REV)
            focusAlpha(100)
            floatShadowRadius(4F)
            floatExpandSize(4F)

            addData(
                SimplePieInfo(
                    5.0,
                    15493974, "To Do" //15493974
                )
            )
            addData(
                SimplePieInfo(
                    6.0,
                    16761172, "in Porgress" //16761172
                )
            )
            addData(
                SimplePieInfo(
                    7.0,
                    4699036, "Done"//4699036
                )
            )
        }

        chart.applyConfig(config)
        chart.start()
    }

    override val inflate: (LayoutInflater, ViewGroup?, attachToRoot: Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate
}