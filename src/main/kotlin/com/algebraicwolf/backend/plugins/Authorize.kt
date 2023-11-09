package com.algebraicwolf.backend.plugins

import com.algebraicwolf.backend.api.getPathParameter
import com.algebraicwolf.backend.storage.PublicationStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*

val Authorize = createRouteScopedPlugin(
    name = "Authorize",
    createConfiguration = ::AuthRequirements
) {
    pluginConfig.apply {
        on(AuthenticationChecked) { call ->
            val id = call.getPathParameter("id")?.toLong()
            if (call.request.httpMethod != HttpMethod.Get && id != null) {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                if (username != pluginConfig.pubStore.getById(id)?.author) {
                    call.respond(HttpStatusCode.Forbidden, "You are not authorized to perform this action")
                }
            }
        }
    }
}

class AuthRequirements {
    lateinit var pubStore : PublicationStorage
}


