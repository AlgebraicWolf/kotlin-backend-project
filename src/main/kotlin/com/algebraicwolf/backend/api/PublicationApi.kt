package com.algebraicwolf.backend.api

import com.algebraicwolf.backend.api.model.ErrorResp
import com.algebraicwolf.backend.api.model.PublicationRequest
import com.algebraicwolf.backend.api.model.PublicationsResp
import com.algebraicwolf.backend.plugins.Authorize
import com.algebraicwolf.backend.storage.PublicationStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import java.lang.Exception

val incorrectFormatErr = "Incorrect format, expected a number"
val pubNotFoundErr = "Publication not found"
val tooLongErr = "Publication is too long. It should be no more than 500 symbols"

fun ApplicationCall.getPathParameter(name: String): String? {
    return this.parameters[name]
}

fun ApplicationCall.getCurrentUser(): String {
    val principal = this.principal<JWTPrincipal>()
    return principal!!.payload.getClaim("username").asString()
}

fun Application.publicationApi() {
    routing {
        val storage by inject<PublicationStorage>()
        authenticate {
            get("/publications") {
                val pubs = storage.getAll()

                call.respond(PublicationsResp(pubs = pubs.toTypedArray()))
            }

            get("/publications/{page}") {
                val page = call.getPathParameter("page")?.toInt()

                if (page != null) {
                    val pubs = storage.getPage(10, page - 1)
                    call.respond(PublicationsResp(pubs = pubs.toTypedArray()))
                } else {
                    call.respond(HttpStatusCode.BadRequest, ErrorResp(incorrectFormatErr))
                }
            }

            put("/publication/create") {
                val request = call.receive<PublicationRequest>()
                val pub = storage.publish(request.pubText, call.getCurrentUser())

                if (pub != null) {
                    call.respond(pub)
                } else {
                    call.respond(HttpStatusCode.BadRequest, ErrorResp(tooLongErr))
                }
            }

            route("/publication/{id}") {
                install(Authorize) {
                    pubStore = storage
                }

                get("/get") {
                    val id = call.getPathParameter("id")?.toLong()

                    if (id != null) {
                        val pub = storage.getById(id)
                        if (pub != null) {
                            call.respond(pub)
                        } else {
                            call.respond(HttpStatusCode.NotFound, ErrorResp(pubNotFoundErr))
                        }
                    } else {
                        call.respond(HttpStatusCode.BadRequest, ErrorResp(incorrectFormatErr))
                    }

                }

                patch("/update") {
                    val id = call.getPathParameter("id")?.toLong()

                    if (id == null) {
                        call.respond(HttpStatusCode.BadRequest, ErrorResp(incorrectFormatErr))
                    } else {
                        val request = call.receive<PublicationRequest>()

                        if (request.pubText.length > 500) {
                            call.respond(HttpStatusCode.BadRequest, ErrorResp(tooLongErr))
                        } else {
                            val res = storage.update(id, request.pubText)
                            if (res) {
                                call.respond(HttpStatusCode.OK)
                            } else {
                                call.respond(HttpStatusCode.NotFound, ErrorResp(pubNotFoundErr))
                            }
                        }
                    }
                }

                delete("/delete") {
                    val id = call.getPathParameter("id")?.toLong()

                    if (id != null) {
                        val res = storage.delete(id)

                        if (res) {
                            call.respond(HttpStatusCode.OK)
                        } else {
                            call.respond(HttpStatusCode.NotFound, ErrorResp(pubNotFoundErr))
                        }
                    } else {
                        call.respond(HttpStatusCode.BadRequest, ErrorResp(incorrectFormatErr))
                    }
                }
            }
        }
    }
}