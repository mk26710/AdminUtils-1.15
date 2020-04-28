package net.defracted.adminutils.util

object Other {
    fun timeDiff(expiresAt: Long, now: Long): HashMap<String, Long> {
        val result = HashMap<String, Long>()

        val diff = expiresAt - now

        result["seconds"] = diff / 1000 % 60
        result["minutes"] = diff / (60 * 1000) % 60
        result["hours"] = diff / (60 * 60 * 1000) % 24
        result["days"] = diff / (24 * 60 * 60 * 1000)

        return result
    }
}