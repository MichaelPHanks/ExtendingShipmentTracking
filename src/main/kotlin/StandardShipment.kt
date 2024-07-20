class StandardShipment(id: String,
                   status: String,
                   notes: MutableList<String>,
                   updateHistory: MutableList<ShippingUpdate>,
                   expectedDeliveryDateTimeStamp: Long,
                   currentLocation: String,
                   creationTime: Long):
    Shipment(id,status, notes, updateHistory, expectedDeliveryDateTimeStamp, currentLocation, creationTime)