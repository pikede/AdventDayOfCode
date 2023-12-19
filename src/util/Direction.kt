package util

enum class Direction {
    Up,
    Down,
    Left,
    Right;

    fun turnSide(direction: Direction): Direction {
        return when {
            this == Left -> {
                when (direction) {
                    Left -> Down
                    Right -> Up
                    Up -> Right
                    Down -> Left
                }
            }
            this == Right -> {
                when (direction) {
                    Right -> Down
                    Left -> Up
                    Up -> Left
                    Down -> Right
                }
            }
            this == Up -> {
                when (direction) {
                    Up -> Up
                    Left -> Left
                    Right -> Right
                    Down -> Down
                }
            }
            this == Down -> {
                when (direction) {
                    Down -> Up
                    Right -> Right
                    Up -> Down
                    Left -> Left
                }
            }
            else -> throw IllegalArgumentException("Illegal direction")
        }
    }
}

