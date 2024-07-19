import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import java.util.*

class TrackerViewHelper : Observer {

    var shipmentExists: Boolean = false
        private set
    var shipmentId by mutableStateOf("")
        private set
    var shipmentNotes = mutableStateListOf<String>()
        private set
    var shipmentUpdateHistory = mutableStateListOf<String>()
        private set
    var expectedShipmentDeliveryDate by mutableStateOf("")
        private set
    var shipmentStatus by mutableStateOf("")
        private set

    var location by mutableStateOf("")
        private set

    // Finds the existing shipment and removes itself from the subscriptions
    fun stopTracking() {
        TrackingServer.findShipment(shipmentId)?.removeSubscription(this)
    }


    // Finds existing shipment and attempts to track it.
    fun trackShipment(id: String)
    {
        val shipment: Shipment? = TrackingServer.findShipment(id)
        this.shipmentId = id
        if (shipment != null)
        {
            shipment.addSubscription(this)
            this.setCard(shipment)
            shipmentExists = true
        }
    }

    // This takes in the 'new' value of the card and sets everything
    private fun setCard(shipment: Shipment)
    {
        this.shipmentId = shipment.getId()
        this.shipmentUpdateHistory.clear()
        this.shipmentNotes.clear()
        for (item in shipment.getUpdateHistory())
        {
            this.shipmentUpdateHistory.add("Shipment went from ${item.getPreviousStatus()} to ${item.getNewStatus()} at ${Date(item.getTimeStamp().toLong())}." )
        }

        for (item in shipment.getNotes())
        {
            this.shipmentNotes.add(item)
        }

        this.shipmentStatus = shipment.getStatus()
        this.location = shipment.getCurrentLocation()
        val time = shipment.getExpectedDeliveryDateTimeStamp()

        if (time < 0)
        {
            this.expectedShipmentDeliveryDate = "--"
        }
        else
        {
            this.expectedShipmentDeliveryDate = shipment.getExpectedDeliveryDateTimeStamp().toString()
        }
    }

    override fun update(shipment: Shipment) {

        if (shipment.getId() == this.shipmentId)
        {
            this.setCard(shipment)
        }

    }

}

