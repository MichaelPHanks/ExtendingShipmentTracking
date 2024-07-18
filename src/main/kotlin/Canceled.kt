class Canceled: Update {
    override fun performUpdate(arguments: List<String>) {
        if (arguments.size != 3)
        {
            throw IllegalArgumentException("Incorrect number of arguments...")
        }

        val shipment: Shipment? = TrackingSimulator.findShipment(arguments[1])

        if (shipment != null)
        {
            if (arguments[2].toLongOrNull() != null) {

                val shippingUpdate = ShippingUpdate(shipment.getStatus(), "canceled", arguments[2].toLong())
                shipment.setStatus("canceled")
                shipment.addUpdate(shippingUpdate)
            }
            else
            {
                throw IllegalArgumentException("Could not parse time stamp")
            }

        }
        else
        {
            throw IllegalArgumentException("Trying to cancel a non-existing shipment")
        }
    }


}