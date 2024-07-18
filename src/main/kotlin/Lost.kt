class Lost: Update {
    override fun performUpdate(arguments: List<String>) {
        if (arguments.size != 3)
        {
            throw IllegalArgumentException("Incorrect number of arguments...")
        }

        val shipment: Shipment? = TrackingSimulator.findShipment(arguments[1])

        if (shipment != null)
        {
            if (arguments[2].toLongOrNull() != null) {

                val shippingUpdate = ShippingUpdate(shipment.getStatus(), "lost", arguments[2].toLong())
                shipment.setStatus("lost")
                shipment.addUpdate(shippingUpdate)
            }
            else
            {
                throw IllegalArgumentException("Could not parse time stamp(s)")
            }

        }
        else
        {
            throw IllegalArgumentException("Trying to set a non-existing shipment to lost")
        }
    }


}