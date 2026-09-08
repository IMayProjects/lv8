package za.org.ecdoe.elevate

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform