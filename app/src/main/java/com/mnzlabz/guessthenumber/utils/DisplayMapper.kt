package com.mnzlabz.guessthenumber.utils

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import com.mnzlabz.guessthenumber.R
import com.mnzlabz.guessthenumber.data.model.SegmentDigit
import com.mnzlabz.guessthenumber.utils.DisplayMapper.Companion.segmentEight
import com.mnzlabz.guessthenumber.utils.DisplayMapper.Companion.segmentFive
import com.mnzlabz.guessthenumber.utils.DisplayMapper.Companion.segmentFour
import com.mnzlabz.guessthenumber.utils.DisplayMapper.Companion.segmentNine
import com.mnzlabz.guessthenumber.utils.DisplayMapper.Companion.segmentOne
import com.mnzlabz.guessthenumber.utils.DisplayMapper.Companion.segmentSeven
import com.mnzlabz.guessthenumber.utils.DisplayMapper.Companion.segmentSix
import com.mnzlabz.guessthenumber.utils.DisplayMapper.Companion.segmentThree
import com.mnzlabz.guessthenumber.utils.DisplayMapper.Companion.segmentTwo
import com.mnzlabz.guessthenumber.utils.DisplayMapper.Companion.segmentZero

class DisplayMapper {

    companion object {
        val segmentZero = listOf(R.id.a, R.id.b, R.id.c, R.id.d, R.id.e, R.id.f)
        val segmentOne = listOf(R.id.b, R.id.c)
        val segmentTwo = listOf(R.id.a, R.id.b, R.id.d, R.id.e, R.id.g)
        val segmentThree = listOf(R.id.a, R.id.b, R.id.c, R.id.d, R.id.g)
        val segmentFour = listOf(R.id.b, R.id.c, R.id.f, R.id.g)
        val segmentFive = listOf(R.id.a, R.id.c, R.id.d, R.id.f, R.id.g)
        val segmentSix = listOf(R.id.a, R.id.c, R.id.d, R.id.e, R.id.f, R.id.g)
        val segmentSeven = listOf(R.id.a, R.id.b, R.id.c)
        val segmentEight = listOf(R.id.a, R.id.b, R.id.c, R.id.d, R.id.e, R.id.f, R.id.g)
        val segmentNine = listOf(R.id.a, R.id.b, R.id.c, R.id.d, R.id.f, R.id.g)
    }

}

fun ConstraintLayout.printSegmentByDigit(digit: String) {
    when(digit) {
        "0" -> { this.zero() }
        "1" -> { this.one() }
        "2" -> { this.two() }
        "3" -> { this.three() }
        "4" -> { this.four() }
        "5" -> { this.five() }
        "6" -> { this.six() }
        "7" -> { this.seven() }
        "8" -> { this.eight() }
        "9" -> { this.nine() }
    }
}

fun ConstraintLayout.zero() {
    this.children.forEach {
        it.alpha = if(segmentZero.contains(it.id) ) 1F else 0.1f
    }
}

fun ConstraintLayout.one() {
    this.children.forEach {
        it.alpha = if(segmentOne.contains(it.id) ) 1F else 0.1f
    }
}

fun ConstraintLayout.two() {
    this.children.forEach {
        it.alpha = if(segmentTwo.contains(it.id) ) 1F else 0.1f
    }
}

fun ConstraintLayout.three() {
    this.children.forEach {
        it.alpha = if(segmentThree.contains(it.id) ) 1F else 0.1f
    }
}

fun ConstraintLayout.four() {
    this.children.forEach {
        it.alpha = if(segmentFour.contains(it.id) ) 1F else 0.1f
    }
}

fun ConstraintLayout.five() {
    this.children.forEach {
        it.alpha = if(segmentFive.contains(it.id) ) 1F else 0.1f
    }
}

fun ConstraintLayout.six() {
    this.children.forEach {
        it.alpha = if(segmentSix.contains(it.id) ) 1F else 0.1f
    }
}

fun ConstraintLayout.seven() {
    this.children.forEach {
        it.alpha = if(segmentSeven.contains(it.id) ) 1F else 0.1f
    }
}

fun ConstraintLayout.eight() {
    this.children.forEach {
        it.alpha = if(segmentEight.contains(it.id) ) 1F else 0.1f
    }
}

fun ConstraintLayout.nine() {
    this.children.forEach {
        it.alpha = if(segmentNine.contains(it.id) ) 1F else 0.1f
    }
}