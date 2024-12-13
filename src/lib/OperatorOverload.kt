package lib

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> =
    this.first + other.first to this.second + other.second

operator fun Triple<Int, Int, Int>.plus(other: Triple<Int, Int, Int>): Triple<Int, Int, Int> =
    Triple(this.first + other.first, this.second + other.second, this.third + other.third)
