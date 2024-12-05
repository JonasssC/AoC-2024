package lib

import java.io.File

fun readInput(year: Int, day: Int): String
    = File(String.format("input/y%d/d%02d/input.txt", year, day))
        .readText()
