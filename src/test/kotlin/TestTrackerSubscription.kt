import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestTrackerSubscription {
    // NOTE: Cannot run suspend function in kotlin tests
    // NOTE: The TrackingSimulator is a static class, testing this can be difficult
    // Just need to make sure that we are changing the shipment id that could or
    // could not exist in the simulation

    @Test
    fun TestTrackerViewHelperUpdated()
    {
        val shipment = Shipment("1234678", "created", mutableListOf(),  mutableListOf(), -1, "unknown")
        val tracker = TrackerViewHelper()
        TrackingServer.addShipment(shipment)
        tracker.trackShipment("1234678")
        assertTrue(tracker.shipmentExists)
        assertEquals("created", tracker.shipmentStatus)
        shipment.setStatus("shipped")

        shipment.addUpdate(ShippingUpdate(shipment.getStatus(), "shipped", "1652712855468".toLong()))
        assertEquals("shipped", tracker.shipmentStatus)




    }

    @Test
    fun TestUnsubscribingFromShipment()
    {
        val shipment = Shipment("12", "created", mutableListOf(),  mutableListOf(), -1, "unknown")
        val tracker = TrackerViewHelper()
        TrackingServer.addShipment(shipment)
        tracker.trackShipment("12")
        assertEquals("created", tracker.shipmentStatus)
        shipment.setStatus("shipped")

        shipment.addUpdate(ShippingUpdate(shipment.getStatus(), "shipped", "1652712855468".toLong()))
        assertEquals("shipped", tracker.shipmentStatus)

        tracker.stopTracking()

        shipment.setStatus("lost")
        assertEquals("shipped", tracker.shipmentStatus)

    }

    // This test will make sure that if there are already notes, updates, etc.,
    // there will be a completed update from the subject.
    @Test
    fun TestUpdatingFilledShipment()
    {
        val shipment = Shipment("123", "created", mutableListOf(),  mutableListOf(), -1, "unknown")
        val tracker = TrackerViewHelper()
        TrackingServer.addShipment(shipment)
        tracker.trackShipment("123")

        shipment.setStatus("delayed")
        assertEquals("delayed", tracker.shipmentStatus)
        shipment.addNote("New note...")
        shipment.addNote("New note...")
        shipment.addNote("New note...")

        shipment.addUpdate(ShippingUpdate(shipment.getStatus(), "shipped", "1652712855468".toLong()))
        shipment.addUpdate(ShippingUpdate(shipment.getStatus(), "lost", "1652712855468".toLong()))
        shipment.addUpdate(ShippingUpdate(shipment.getStatus(), "delayed", "1652712855468".toLong()))

        shipment.setExpectedDeliveryDateTimeStamp(12345)
        assertEquals(3, tracker.shipmentNotes.size)
        assertEquals(3, tracker.shipmentUpdateHistory.size)
        tracker.stopTracking()

    }


}