package cl.optisoft.network.client

import io.ktor.client.HttpClient

interface PlatformHttpClientProvider {
    fun provide(): HttpClient
}

expect fun createHttpClientProvider(): PlatformHttpClientProvider