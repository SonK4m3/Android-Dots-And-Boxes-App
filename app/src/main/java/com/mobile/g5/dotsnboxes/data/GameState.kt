package com.mobile.g5.dotsnboxes.data

@kotlinx.serialization.Serializable
data class GameState(
    val playerAtTurn: Int? = 1,
    val field: Array<Array<Rope?>> = emptyField(),
    val boxes: Array<Array<Int?>> = emptyBoxes(),
    val winningPlayer: Int? = null,
    val isBoardFull: Boolean = false,
    val connectedPlayers: List<Int> = emptyList(),
) {
    companion object {
        const val DOTS = 6
        const val BOXES = 5
        fun emptyField(): Array<Array<Rope?>> {
            return Array (DOTS) {// vertical
                Array(DOTS) {// horizontal
                    null
                }
            }
        }

        fun emptyBoxes(): Array<Array<Int?>> {
            return Array (BOXES) {
                Array(BOXES) {
                    null
                }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameState

        if (playerAtTurn != other.playerAtTurn) return false
        if (!field.contentDeepEquals(other.field)) return false
        if (!boxes.contentDeepEquals(other.boxes)) return false
        if (winningPlayer != other.winningPlayer) return false
        if (isBoardFull != other.isBoardFull) return false
        if (connectedPlayers != other.connectedPlayers) return false

        return true
    }

    override fun hashCode(): Int {
        var result = playerAtTurn ?: 0
        result = 31 * result + field.contentDeepHashCode()
        result = 31 * result + boxes.contentDeepHashCode()
        result = 31 * result + (winningPlayer ?: 0)
        result = 31 * result + isBoardFull.hashCode()
        result = 31 * result + connectedPlayers.hashCode()
        return result
    }
}