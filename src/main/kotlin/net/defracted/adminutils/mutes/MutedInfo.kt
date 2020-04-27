package net.defracted.adminutils.mutes

class MutedInfo {
    val moderator: String
    val reason: String
    val expiresAt: Long

    constructor(moderator: String, reason: String, expiresAt: Long) {
        this.moderator = moderator
        this.reason = reason
        this.expiresAt = expiresAt
    }
}