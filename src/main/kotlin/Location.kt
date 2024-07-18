class Location: Update {
    override fun performUpdate(arguments: List<String>) {
        if (arguments.size != 4)
        {
            throw IllegalArgumentException("Not enough arguments for creating shipment...")

        }
        val shipment: Shipment? = TrackingSimulator.findShipment(arguments[1])

        if (shipment != null)
        {
            if (arguments[2].toLongOrNull() != null)
            {
                shipment.setCurrentLocation(arguments[3])

            }
        else
            {
                throw IllegalArgumentException("Could not parse time stamp(s)")
            }
        }
        else
        {
            throw IllegalArgumentException("Trying to set a location to a non-existent shipment")
        }
    }


}