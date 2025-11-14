package cl.multicaja.optisoft_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform