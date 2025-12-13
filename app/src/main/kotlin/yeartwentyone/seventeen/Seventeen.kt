package yeartwentyone.seventeen

// Part 1: 9730
// Part 2: 4110
fun main() {
    var highest = 0
    var cntDistinctVelocity = 0
    for (x in -1000..1000) {
        for (y in -1000..1000) {
            val shot = Shot(x, y, 117, 164, -89, -140)

            while (shot.isAboveBottomBound()) {
                shot.moveVelocity()
                if (shot.isInRange()) {
                    cntDistinctVelocity++
                    highest = maxOf(highest, shot.getHighestY())
                    break
                }
            }
        }
    }

    println(highest)
    println(cntDistinctVelocity)
}

class Shot(
    xVelocity: Int,
    yVelocity: Int,
    leftBound: Int,
    rightBound: Int,
    upBound: Int,
    downBound: Int
) {
    private var x = 0
    private var y = 0
    private var highest = 0

    private var yV: Int
    private var xV: Int
    private val leftBound: Int
    private val rightBound: Int
    private val upBound: Int
    private val downBound: Int

    init {
        this.xV = xVelocity
        this.yV = yVelocity
        this.leftBound = leftBound
        this.rightBound = rightBound
        this.upBound = upBound
        this.downBound = downBound
    }


    fun moveVelocity() {
        this.x += xV
        this.y += yV

        if (this.xV > 0) {
            this.xV--
        } else if (this.xV < 0) {
            this.xV++
        }
        this.yV--
        highest = maxOf(highest, y)
    }

    fun getHighestY() = highest

    // x ascending range check, y descending check
    fun isInRange() = x in leftBound..rightBound && y in upBound downTo downBound
    fun isAboveBottomBound() = y > downBound
}