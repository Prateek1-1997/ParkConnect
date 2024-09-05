package com.vehicle.owner.network

import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException

class CurlLoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBody = request.body

        val curlCommand =
            buildString {
                append("curl -X ${request.method} \"${request.url}\"")

                request.headers.forEach { (name, value) ->
                    append(" -H \"$name: $value\"")
                }

                requestBody?.let { body ->
                    val buffer = Buffer()
                    body.writeTo(buffer)
                    val bodyString = buffer.readUtf8()
                    append(" --data '${bodyString.escapeSingleQuotes()}'")
                }
            }

        println("CurlCommand: $curlCommand")

        return chain.proceed(request)
    }

    // curl command formatting
    private fun String.escapeSingleQuotes(): String {
        return this.replace("'", "\\'")
    }
}
