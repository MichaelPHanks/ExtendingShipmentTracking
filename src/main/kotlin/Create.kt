import javax.print.attribute.standard.MediaSize.NA

class Create: Update {
    override fun performUpdate(arguments: List<String>) {

        if (arguments.size != 3)
        {
            throw IllegalArgumentException("Not enough arguments for creating shipment...")

        }
        val shipment: Shipment? =  TrackingSimulator.findShipment(arguments[1])

        if (shipment == null)
        {
            if (arguments[2].toLongOrNull() != null) {

                TrackingSimulator.addShipment(
                    Shipment(
                        arguments[1],
                        "created",
                        mutableListOf(),
                        mutableListOf(),
                        -1,
                        "unknown"
                    )
                )
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