package com.mobile.g5.dotsnboxes.data

@kotlinx.serialization.Serializable
data class GameState(
    val playerAtTurn: Int? = 0,
    val field: Array<Array<Int?>> = emptyField(),
) {
    companion object {
        fun emptyField(): Array<Array<Int?>> {
            return arrayOf(
                arrayOf(null, null, null),
                arrayOf(null, null, null),
                arrayOf(null, null, null),
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameState

        if (playerAtTurn != other.playerAtTurn) return false
        if (!field.contentDeepEquals(other.field)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = playerAtTurn ?: 0
        result = 31 * result + field.contentDeepHashCode()
        return result
    }
}