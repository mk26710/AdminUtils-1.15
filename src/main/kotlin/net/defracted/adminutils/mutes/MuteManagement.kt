package net.defracted.adminutils.mutes

import java.util.*
import kotlin.collections.HashMap

class MuteManagement {
    val mutedPlayers = HashMap<UUID, MutedInfo>()

    fun addMute(target: UUID, moderator: String, reason: String, expiresAt: Long) {
        mutedPlayers[target] = MutedInfo(moderator, reason, expiresAt)
    }

    fun delMute(target: UUID) {
        mutedPlayers.remove(target)
    }

}