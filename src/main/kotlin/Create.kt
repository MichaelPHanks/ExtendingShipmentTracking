import javax.print.attribute.standard.MediaSize.NA

class Create: Update {
    override fun performUpdate(arguments: List<String>) {

        if (arguments.size != 4)
        {
            throw IllegalArgumentException("Not enough arguments for creating shipment...")

        }
        val shipment: Shipment? =  TrackingServer.findShipment(arguments[1])

        if (shipment == null)
        {
            if (arguments[3].toLongOrNull() != null) {

                val shipment1: Shipment = createShipment(arguments)

                TrackingServer.addShipment(shipment1)

            }
            else
            {
                throw IllegalArgumentException("Could not parse time stamp(s)")

            }

        }
        else
        {
            throw IllegalArgumentException("Shipment already exists and cannot be created...")
        }
    }


}