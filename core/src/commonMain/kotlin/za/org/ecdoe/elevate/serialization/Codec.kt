package za.org.ecdoe.elevate.serialization

import kotlinx.serialization.json.Json

object Codec {
    val jsonCodec: Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }
}