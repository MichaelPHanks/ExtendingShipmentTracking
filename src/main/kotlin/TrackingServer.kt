import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

suspend fun server()
{

    embeddedServer(Netty, 8080)
    {
        routing {
            get("/") {
                call.respondText(File("src/main/index.html").readText(), ContentType.Text.Html)
            }
            post("/data")
            {
                val data = call.receiveText()
                TrackingSimulator.addShipment(
                    Shipment(
                        data,
                        "created",
                        mutableListOf(),
                        mutableListOf(),
                        -1,
                        "unknown"
                    )
                )
                println(data)
                call.respondText{"Okay!"}
            }


        }
    }.start(wait = false)
}