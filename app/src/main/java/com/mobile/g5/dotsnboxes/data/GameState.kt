package com.mobile.g5.dotsnboxes.data

@kotlinx.serialization.Serializable
data class GameState(
    val playerAtTurn: Int? = 0,
    val field: Array<Array<Int?>> = emptyField(),
    val winningPlayer: Int? = null,
    val isBoardFull: Boolean = false,
    val connectedPlayers: List<Int> = emptyList(),
) {
    companion object {
        fun emptyField(): Array<Array<Int?>> {
            return arrayOf(
                arrayOf(null, null, null, null, null),  // vertical 1
                arrayOf(null, null, null, null, null),
                arrayOf(null, null, null, null, null),
                arrayOf(null, null, null, null, null),
                arrayOf(null, null, null, null, null),
                arrayOf(null, null, null, null, null),  // vertical 6
                arrayOf(null, null, null, null, null),  // horizontal 1
                arrayOf(null, null, null, null, null),
                arrayOf(null, null, null, null, null),
                arrayOf(null, null, null, null, null),
                arrayOf(null, null, null, null, null),
                arrayOf(null, null, null, null, null),  // horizontal 6
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameState

        if (playerAtTurn != other.playerAtTurn) return false
        if (!field.contentDeepEquals(other.field)) return false
        if (winningPlayer != other.winningPlayer) return false
        if (isBoardFull != other.isBoardFull) return false
        if (connectedPlayers != other.connectedPlayers) return false

        return true
    }

    override fun hashCode(): Int {
        var result = playerAtTurn ?: 0
        result = 31 * result + field.contentDeepHashCode()
        result = 31 * result + (winningPlayer ?: 0)
        result = 31 * result + isBoardFull.hashCode()
        result = 31 * result + connectedPlayers.hashCode()
        return result
    }
}