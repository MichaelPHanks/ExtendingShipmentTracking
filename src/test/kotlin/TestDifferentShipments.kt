import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class TestDifferentShipments {
    @Test
    fun TestOvernightShipmentBehavior()
    {
        val arguments: List<String> = listOf("created", "overnightShipment", "overnight", "1234")
        val shipment: Shipment = createShipment(arguments)

        TrackingServer.addShipment(shipment)
        assertEquals("overnightShipment", shipment.getId())
        assertEquals("created", shipment.getStatus())
        assertEquals(-1, shipment.getExpectedDeliveryDateTimeStamp())
        assertEquals(1234, shipment.getCreationTime())

        val shipped = Shipped()
        val arguments2: List<String> = listOf("shipped", "overnightShipment", "1234","123412341234")
        shipped.performUpdate(arguments2)
        assertEquals(1, shipment.getNotes().size)
        assertEquals(1, shipment.getUpdateHistory().size)

        assertFailsWith<IllegalArgumentException> { shipped.performUpdate(arguments2) }
    }
    @Test
    fun TestExpressShipmentBehavior()
    {
        val arguments: List<String> = listOf("created", "expressShipment", "express", "1234")
        val shipment: Shipment = createShipment(arguments)

        TrackingServer.addShipment(shipment)
        assertEquals("expressShipment", shipment.getId())
        assertEquals("created", shipment.getStatus())
        assertEquals(-1, shipment.getExpectedDeliveryDateTimeStamp())
        assertEquals(1234, shipment.getCreationTime())

        val shipped = Shipped()
        val arguments2: List<String> = listOf("shipped", "expressShipment", "1234","123412341234")
        shipped.performUpdate(arguments2)
        assertEquals(1, shipment.getNotes().size)
        assertEquals(1, shipment.getUpdateHistory().size)

        assertFailsWith<IllegalArgumentException> { shipped.performUpdate(arguments2) }
    }
    @Test
    fun TestBulkShipmentBehavior()
    {
        val arguments: List<String> = listOf("created", "bulkShipment", "bulk", "1234")
        val shipment: Shipment = createShipment(arguments)

        TrackingServer.addShipment(shipment)
        assertEquals("bulkShipment", shipment.getId())
        assertEquals("created", shipment.getStatus())
        assertEquals(-1, shipment.getExpectedDeliveryDateTimeStamp())
        assertEquals(1234, shipment.getCreationTime())

        val shipped = Shipped()
        val arguments2: List<String> = listOf("shipped", "bulkShipment", "1234","1234")
        shipped.performUpdate(arguments2)
        assertEquals(1, shipment.getNotes().size)
        assertEquals(1, shipment.getUpdateHistory().size)

        assertFailsWith<IllegalArgumentException> { shipped.performUpdate(arguments2) }

    }
}