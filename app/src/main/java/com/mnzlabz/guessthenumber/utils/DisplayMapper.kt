package com.mnzlabz.guessthenumber.utils

class DisplayMapper {

    companion object {
        private val mappings = mapOf(
            "0" to "a@b@c@d@e@f",
            "1" to "b@c",
            "2" to "a@b@g@e@d",
            "3" to "a@b@c@d@g",
            "4" to "b@c@f@g",
            "5" to "a@f@g@c@d",
            "6" to "a@f@g@c@d@e",
            "7" to "a@b@c",
            "8" to "a@b@c@d@e@f@g",
            "9" to "a@b@c@d@f@g"
        )

        fun getSegmentsFromDigit(digit : String): List<String> = mappings[digit].toString().split("@")
    }

}