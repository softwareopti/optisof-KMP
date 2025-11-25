package cl.optisoft.network.client

import cl.optisoft.network.di.API_RETRY_ON_CONNECTION_FAILURE
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp

class AndroidHttpClientProvider : PlatformHttpClientProvider {
    override fun provide(): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                config {
                    retryOnConnectionFailure(API_RETRY_ON_CONNECTION_FAILURE)
                }
            }
        }
    }
}

actual fun createHttpClientProvider(): PlatformHttpClientProvider {
    return AndroidHttpClientProvider()
}