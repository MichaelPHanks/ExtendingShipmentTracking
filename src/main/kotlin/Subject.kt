
interface Subject {
    fun addSubscription(observer: Observer)

    fun removeSubscription(observer: Observer)

    fun notifyObservers(shipment: Shipment)

}