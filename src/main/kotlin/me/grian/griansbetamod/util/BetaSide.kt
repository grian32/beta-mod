package me.grian.griansbetamod.util

enum class BetaSide(val value: Int) {
    BOTTOM(0),
    TOP(1),
    NORTH(2),
    SOUTH(3),
    WEST(4),
    EAST(5);

    companion object {
        @JvmStatic
        fun getStairsDirection(meta: Int): BetaSide {
            return when(meta) {
                0 -> BetaSide.WEST
                1 -> BetaSide.EAST
                2 -> BetaSide.NORTH
                3 -> BetaSide.SOUTH
                else -> BetaSide.BOTTOM
            }
        }
    }
}
