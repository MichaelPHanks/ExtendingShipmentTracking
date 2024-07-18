import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object TrackingSimulator {

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

    // Main simulation for application
    suspend fun runSimulation()
    {
        val fileName = "src/jvmMain/kotlin/test.txt"
        val fileLines = File(fileName).readLines()
        fileLines.forEach {

            val temp = it.split(",")
            updates[temp[0]]?.performUpdate(temp)

            withContext(Dispatchers.IO) {
                Thread.sleep(1000)
            }
        }
    }


}