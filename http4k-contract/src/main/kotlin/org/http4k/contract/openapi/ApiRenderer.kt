package org.http4k.contract.openapi

import org.http4k.format.JsonLibAutoMarshallingJson
import org.http4k.util.JsonSchemaCreator
import java.util.concurrent.atomic.AtomicReference

/**
 * Renders the contract contents in OpenApi JSON format.
 */
interface ApiRenderer<API, NODE> : JsonSchemaCreator<Any, NODE> {
    fun api(api: API): NODE

    companion object {
        /**
         * ApiRenderer which uses auto-marshalling JSON to create JSON schema for message models.
         */
        fun <T : Any, NODE : Any> Auto(json: JsonLibAutoMarshallingJson<NODE>,
                                       schema: JsonSchemaCreator<Any, NODE>): ApiRenderer<T, NODE> =
            object : ApiRenderer<T, NODE>, JsonSchemaCreator<Any, NODE> by schema {
                override fun api(api: T) = json.asJsonObject(api)
            }
    }
}

/**
 * Cache the result of the API render, in case it is expensive to calculate.
 */
fun <API : Any, NODE : Any> ApiRenderer<API, NODE>.cached(): ApiRenderer<API, NODE> = object : ApiRenderer<API, NODE> by this {
    private val cached = AtomicReference<NODE>()
    override fun api(api: API): NODE = cached.get() ?: this@cached.api(api).also { cached.set(it) }
}