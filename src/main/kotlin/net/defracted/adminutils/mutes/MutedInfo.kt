package net.defracted.adminutils.mutes

import java.util.*

class MutedInfo {
    val target: UUID
    val moderator: String
    val reason: String
    val expiresAt: Date

    constructor(target: UUID, moderator: String, reason: String, expiresAt: Date) {
        this.target = target
        this.moderator = moderator
        this.reason = reason
        this.expiresAt = expiresAt
    }
}