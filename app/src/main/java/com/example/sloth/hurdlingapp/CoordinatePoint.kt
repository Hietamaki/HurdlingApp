package com.example.sloth.hurdlingapp

import android.graphics.Point

/**
 * A Point that has parse function.
 */
open class CoordinatePoint : Point {
    constructor(x: Int, y: Int) : super(x, y)

    override fun toString(): String {
        return "$x $y"
    }
    companion object {

        fun parsePoint(s: String): CoordinatePoint {
            //Check if String is in proper format.
            if(s.matches(Constants.REGEX_COORDINATE_POINT_FORMAT.toRegex())) {
                val splitString = s.split(" ")
                val point = CoordinatePoint(splitString[0].toInt(), splitString[1].toInt())
                return point
            }
            throw Exception("String is not in proper format")
            return CoordinatePoint(0,0)
        }
    }
}

