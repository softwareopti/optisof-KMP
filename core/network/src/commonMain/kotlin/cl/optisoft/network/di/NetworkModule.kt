package cl.optisoft.network.di

import cl.optisoft.network.HttpClientLogger
import cl.optisoft.network.client.createHttpClientProvider
import cl.optisoft.network.qualifier.BaseUrlQualifier
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val networkModule = module {
//    factory(BaseUrlQualifier) { "https://optisof-api.onrender.com" }
    factory(BaseUrlQualifier) { "http://10.0.2.2:3006" }

    singleOf(::createJson)
    single {
        createConfiguredClient(get(BaseUrlQualifier), get())
    }
}

private fun HttpClientConfig<*>.configureLogging(baseUrl: String) {
    install(Logging) {
        level = LogLevel.BODY
        logger = HttpClientLogger

        filter { it.url.toString().startsWith(baseUrl) }
    }
}

private fun HttpClientConfig<*>.configureContentNegotiation(json: Json) {
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = false
                isLenient = true
            }
        )
    }
}

private fun HttpClientConfig<*>.configureTimeout() {
    install(HttpTimeout) {
        requestTimeoutMillis = API_REQUEST_TIMEOUT_MILLIS
        connectTimeoutMillis = API_CONNECT_TIMEOUT_SECONDS
        socketTimeoutMillis = API_SOCKET_TIMEOUT_SECONDS
    }
}


private fun HttpClientConfig<*>.configureDefaultRequest(
    baseUrl: String
) {
    defaultRequest {
        url(baseUrl)
        contentType(ContentType.Application.Json)
    }
}

private fun createConfiguredClient(
    baseUrl: String,
    json: Json
): HttpClient {
    return createHttpClientProvider().provide().config {
        configureLogging(baseUrl)
        configureContentNegotiation(json)
        configureTimeout()
        configureDefaultRequest(baseUrl)
    }
}

private fun createJson() = Json {
    isLenient = true
    explicitNulls = true
    prettyPrint = true
    ignoreUnknownKeys = true
    coerceInputValues = true
    encodeDefaults = true
}


const val API_REQUEST_TIMEOUT_MILLIS = 30_000L
const val API_CONNECT_TIMEOUT_SECONDS = 30_000L
const val API_SOCKET_TIMEOUT_SECONDS = 30_000L

const val API_RETRY_ON_CONNECTION_FAILURE = true