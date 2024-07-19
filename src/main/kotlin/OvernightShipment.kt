import java.util.Date

class OvernightShipment(id: String,
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
        if (Date(this.getExpectedDeliveryDateTimeStamp()) > Date(this.getCreationTime() + (1000 * 60 * 60 * 24)) && this.getStatus() != "delayed")
        {
            // Add a note!
            if (!this.getNotes().contains("Shipment is expected to arrive later than the expected less than one day, sorry!"))
            {
                this.addNote("Shipment is expected to arrive later than the expected less than one day, sorry!")
            }
        }
        notifyObservers(this)

    }

}