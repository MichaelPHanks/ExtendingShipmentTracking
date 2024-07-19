class StandardShipment(id: String,
                   status: String,
                   notes: MutableList<String>,
                   updateHistory: MutableList<ShippingUpdate>,
                   expectedDeliveryDateTimeStamp: Long,
                   currentLocation: String,
                   creationTime: Long):
    Shipment(id,status, notes, updateHistory, expectedDeliveryDateTimeStamp, currentLocation, creationTime)
{
//    override fun addUpdate(update: ShippingUpdate) {
//
//        this.updateHistory.add(update)
//        notifyObservers(this)
//
//    }

}