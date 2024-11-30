package lib

import java.io.File

fun readStr(year: Int, day: Int): String
    = File(String.format("out/production/AoC-2024/y%d/d%02d/input.txt", year, day))
        .readText().trim()
