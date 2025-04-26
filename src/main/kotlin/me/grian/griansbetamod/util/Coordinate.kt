package me.grian.griansbetamod.util

@JvmInline
value class Coordinate(
    val packed: Long
) {
    constructor(x: Int, y: Short, z: Int) : this(pack(x, y, z))

    val x: Int
        get() = ((packed shr 25) and 0x1FFFFFF).toInt()

    val y: Short
        get() = ((packed shr 50) and 0xFF).toShort()

    val z: Int
        get() = ((packed) and 0X1FFFFFF).toInt()

    companion object {
        fun pack(x: Int, y: Short, z: Int): Long {
            val xLong = x.toLong()
            val yLong = y.toLong()
            val zLong = z.toLong()

            return (xLong and 0x1FFFFFF shl 25) or (zLong and 0x1FFFFFF) or (yLong and 0xFF shl 50)
        }
    }
}
