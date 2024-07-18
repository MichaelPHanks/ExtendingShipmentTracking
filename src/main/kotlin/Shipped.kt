class Shipped: Update {
    override fun performUpdate(arguments: List<String>) {

        if (arguments.size != 4)
        {
            throw IllegalArgumentException("Incorrect number of arguments...")
        }

        val shipment: Shipment? = TrackingSimulator.findShipment(arguments[1])

        if (shipment != null)
        {
            if (arguments[2].toLongOrNull() != null && arguments[3].toLongOrNull() != null) {

                val shippingUpdate = ShippingUpdate(shipment.getStatus(), "shipped", arguments[2].toLong())
                shipment.setStatus("shipped")
                shipment.setExpectedDeliveryDateTimeStamp(arguments[3].toLong())
                shipment.addUpdate(shippingUpdate)
            }
            else
            {
                throw IllegalArgumentException("Could not parse time stamp(s)")
            }

        }
        else
        {
            throw IllegalArgumentException("Trying to ship a non-existing shipment")
        }
    }


}