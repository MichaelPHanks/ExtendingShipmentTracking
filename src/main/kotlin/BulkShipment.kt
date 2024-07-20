import java.util.Date

class BulkShipment(id: String,
                   status: String,
                   notes: MutableList<String>,
                   updateHistory: MutableList<ShippingUpdate>,
                   expectedDeliveryDateTimeStamp: Long,
                   currentLocation: String,
                   creationTime: Long):
    Shipment(id,status, notes, updateHistory, expectedDeliveryDateTimeStamp, currentLocation, creationTime)
{
        override fun addUpdate(update: ShippingUpdate) {
            super.addUpdate(update)
            if (Date(this.getExpectedDeliveryDateTimeStamp()) < Date(this.getCreationTime() + (1000 * 60 * 60 * 24 * 3)) && this.getStatus() == "shipped")
            {
                // Add a note!
                if (!this.getNotes().contains("Shipment is expected to arrive earlier than the required 3 days for a bulk shipment, congrats!"))
                {
                    this.addNote("Shipment is expected to arrive earlier than the required 3 days for a bulk shipment, congrats!")
                }
            }
            notifyObservers(this)

    }

}