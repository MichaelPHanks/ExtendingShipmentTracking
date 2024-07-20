import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TestShipmentFactory {
    @Test
    fun TestCreatingStandardShipment()
    {
        val arguments: List<String> = listOf("created", "standardShipment", "standard", "1234")
        val shipment: Shipment = createShipment(arguments)

        assertEquals("standardShipment", shipment.getId())
        assertEquals("created", shipment.getStatus())
        assertEquals(-1, shipment.getExpectedDeliveryDateTimeStamp())
        assertEquals(1234, shipment.getCreationTime())

    }
    @Test
    fun TestCreatingBulkShipment()
    {
        val arguments: List<String> = listOf("created", "bulkShipment", "bulk", "1234")
        val shipment: Shipment = createShipment(arguments)

        assertEquals("bulkShipment", shipment.getId())
        assertEquals("created", shipment.getStatus())
        assertEquals(-1, shipment.getExpectedDeliveryDateTimeStamp())
        assertEquals(1234, shipment.getCreationTime())
    }
    @Test
    fun TestCreatingExpressShipment()
    {
        val arguments: List<String> = listOf("created", "expressShipment", "express", "1234")
        val shipment: Shipment = createShipment(arguments)

        assertEquals("expressShipment", shipment.getId())
        assertEquals("created", shipment.getStatus())
        assertEquals(-1, shipment.getExpectedDeliveryDateTimeStamp())
        assertEquals(1234, shipment.getCreationTime())
    }
    @Test
    fun TestCreatingOvernightShipment()
    {
        val arguments: List<String> = listOf("created", "overnightShipment", "overnight", "1234")
        val shipment: Shipment = createShipment(arguments)

        assertEquals("overnightShipment", shipment.getId())
        assertEquals("created", shipment.getStatus())
        assertEquals(-1, shipment.getExpectedDeliveryDateTimeStamp())
        assertEquals(1234, shipment.getCreationTime())
    }
    @Test
    fun TestCreatingNonExistingShipment()
    {
        val arguments: List<String> = listOf("created", "fakeShipment", "newShipmentType", "1234")
        assertFailsWith<IllegalArgumentException> {createShipment(arguments)}
    }
    @Test
    fun TestIncorrectNumberOfArguments()
    {
        val arguments: List<String> = listOf("created", "fakeShipment", "anotherFakeShipment")
        assertFailsWith<IllegalArgumentException> {createShipment(arguments)}
    }
}