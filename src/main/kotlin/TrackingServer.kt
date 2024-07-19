import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

object TrackingServer
{
    private var shipments: MutableList<Shipment> = mutableListOf()

    private var updates: Map<String, Update> = mapOf(
        "created" to Create(),
        "shipped" to Shipped(),
        "location" to Location(),
        "delivered" to Delivered(),
        "delayed" to Delayed(),
        "lost" to Lost(),
        "canceled" to Canceled(),
        "noteadded" to NoteAdded()

    )


    fun findShipment(id: String): Shipment?
    {
        return shipments.find { it.getId() == id }
    }

    fun addShipment(shipment: Shipment)
    {
        this.shipments.add(shipment)
    }
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
//                    TrackingSimulator.addShipment(
//                        Shipment(
//                            data,
//                            "created",
//                            mutableListOf(),
//                            mutableListOf(),
//                            -1,
//                            "unknown"
//                        )
//                    )
                    try {
                        val temp = data.split(",")
                        updates[temp[0]]?.performUpdate(temp)
                        for (item in shipments)
                        {
                            println(item.getId())
                        }
                    }
                    catch (e: Exception)
                    {
                        println("Error!: $e")
                    }
                    println(data)
                    call.respondText{"Okay!"}
                }
            }
        }.start(wait = false)
    }
}





