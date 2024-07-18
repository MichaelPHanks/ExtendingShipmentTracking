class ShippingUpdate(
    private var previousStatus: String,
    private var newStatus: String,
    private var timeStamp: Long) {

    fun getPreviousStatus(): String
    {
        return this.previousStatus
    }



    fun getNewStatus(): String
    {
        return this.newStatus
    }



    fun getTimeStamp(): Long
    {
        return this.timeStamp
    }


}