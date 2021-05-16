package ru.frozenpriest.taskautomaton.data.local

import android.net.Uri
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer

@JsonSerialize(using = UriSerializer::class)
@JsonDeserialize(using = UriDeserializer::class)
interface UriMixIn

class UriDeserializer : StdDeserializer<Uri>(Uri::class.java) {

    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): Uri {
        return Uri.parse(p?.valueAsString)
    }
}

class UriSerializer : StdSerializer<Uri>(Uri::class.java) {

    override fun serialize(value: Uri?, gen: JsonGenerator?, provider: SerializerProvider?) {
        gen?.writeString(value?.toString())
    }
}