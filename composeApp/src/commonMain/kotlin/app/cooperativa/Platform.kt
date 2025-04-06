package app.cooperativa

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform