import org.jetbrains.annotations.TestOnly
import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class TestShipmentUpdate {


    @Test
    fun TestCreateShipment()
    {
        val create = Create()
        val arguments: MutableList<String> = mutableListOf("created", "newShipment","standard", "12345678")

        create.performUpdate(arguments)

        val shipment: Shipment? = TrackingServer.findShipment("newShipment")

        assertNotNull(shipment)
        assertEquals("created", shipment.getStatus())
        assertEquals(0, shipment.getUpdateHistory().size)
        assertEquals(-1, shipment.getExpectedDeliveryDateTimeStamp())
        assertEquals(0, shipment.getNotes().size)

    }
    @Test
    fun TestShippedShipment()
    {

        val shipment = StandardShipment("98", "created", mutableListOf(),  mutableListOf(), -1, "unknown", 12345678)

        TrackingServer.addShipment(shipment)
        val shipped = Shipped()
        val arguments: MutableList<String> = mutableListOf("shipped", "98", "12345678", "12345678")

        shipped.performUpdate(arguments)

        val shipment1: Shipment? = TrackingServer.findShipment("98")

        assertNotNull(shipment1)
        assertEquals("shipped", shipment1.getStatus())
        assertEquals(1, shipment1.getUpdateHistory().size)
        assertEquals(12345678, shipment1.getExpectedDeliveryDateTimeStamp())
        assertEquals(0, shipment1.getNotes().size)

        // Testing to make sure we cannot ship it again!
        assertFailsWith<IllegalArgumentException> { shipped.performUpdate(arguments) }

    }
    @Test
    fun TestLostShipment()
    {
        val shipment = StandardShipment("99", "created", mutableListOf(),  mutableListOf(), -1, "unknown", 12345678)

        TrackingServer.addShipment(shipment)
        val lost = Lost()
        val arguments: MutableList<String> = mutableListOf("lost", "99", "12345678")

        lost.performUpdate(arguments)

        val shipment1: Shipment? = TrackingServer.findShipment("99")

        assertNotNull(shipment1)
        assertEquals("lost", shipment1.getStatus())
        assertEquals(1, shipment1.getUpdateHistory().size)
        assertEquals(-1, shipment1.getExpectedDeliveryDateTimeStamp())
        assertEquals(0, shipment1.getNotes().size)

    }
    @Test
    fun TestLocationShipment()
    {
        val shipment = StandardShipment("100", "created", mutableListOf(),  mutableListOf(), -1, "unknown", 12345678)

        TrackingServer.addShipment(shipment)
        val location = Location()
        val arguments: MutableList<String> = mutableListOf("location", "100", "12345678", "Salt Lake City, UT")

        location.performUpdate(arguments)

        val shipment1: Shipment? = TrackingServer.findShipment("100")

        assertNotNull(shipment1)
        assertEquals("created", shipment1.getStatus())
        assertEquals(0, shipment1.getUpdateHistory().size)
        assertEquals(-1, shipment1.getExpectedDeliveryDateTimeStamp())
        assertEquals(0, shipment1.getNotes().size)

    }

    @Test
    fun TestAddingNote()
    {
        val shipment = StandardShipment("101", "created", mutableListOf(),  mutableListOf(), -1, "unknown", 12345678)

        TrackingServer.addShipment(shipment)
        val note = NoteAdded()
        val arguments: MutableList<String> = mutableListOf("location", "101", "12345678", "something happened to the package!!!")

        note.performUpdate(arguments)

        val shipment1: Shipment? = TrackingServer.findShipment("101")

        assertNotNull(shipment1)
        assertEquals("created", shipment1.getStatus())
        assertEquals(0, shipment1.getUpdateHistory().size)
        assertEquals(-1, shipment1.getExpectedDeliveryDateTimeStamp())
        assertEquals(1, shipment1.getNotes().size)
    }

    @Test
    fun TestDeliveringShipment()
    {
        val shipment = StandardShipment("102", "created", mutableListOf(),  mutableListOf(), -1, "unknown", 12345678)

        TrackingServer.addShipment(shipment)
        val delivery = Delivered()
        val arguments: MutableList<String> = mutableListOf("delivered", "102", "12345678")

        delivery.performUpdate(arguments)

        val shipment1: Shipment? = TrackingServer.findShipment("102")

        assertNotNull(shipment1)
        assertEquals("delivered", shipment1.getStatus())
        assertEquals(1, shipment1.getUpdateHistory().size)
        assertEquals(-1, shipment1.getExpectedDeliveryDateTimeStamp())
        assertEquals(0, shipment1.getNotes().size)
    }

    @Test
    fun TestCancelingShipment()
    {
        val shipment = StandardShipment("103", "created", mutableListOf(),  mutableListOf(), -1, "unknown", 12345678)

        TrackingServer.addShipment(shipment)
        val cancel = Canceled()
        val arguments: MutableList<String> = mutableListOf("canceled", "103", "12345678")

        cancel.performUpdate(arguments)

        val shipment1: Shipment? = TrackingServer.findShipment("103")

        assertNotNull(shipment1)
        assertEquals("canceled", shipment1.getStatus())
        assertEquals(1, shipment1.getUpdateHistory().size)
        assertEquals(-1, shipment1.getExpectedDeliveryDateTimeStamp())
        assertEquals(0, shipment1.getNotes().size)
    }

    @Test
    fun TestDelayingShipment()
    {
        val shipment = StandardShipment("104", "created", mutableListOf(),  mutableListOf(), -1, "unknown", 12345678)

        TrackingServer.addShipment(shipment)
        val delay = Delayed()
        val arguments: MutableList<String> = mutableListOf("delayed", "104", "12345678", "12345679")

        delay.performUpdate(arguments)

        val shipment1: Shipment? = TrackingServer.findShipment("104")

        assertNotNull(shipment1)
        assertEquals("delayed", shipment1.getStatus())
        assertEquals(1, shipment1.getUpdateHistory().size)
        assertEquals(12345679, shipment1.getExpectedDeliveryDateTimeStamp())
        assertEquals(0, shipment1.getNotes().size)
    }



    @Test
    fun TestFailingLongCheck()
    {
        val shipment = StandardShipment("1234", "created", mutableListOf(),  mutableListOf(), -1, "unknown", 12345678)
        val arguments: MutableList<String> = mutableListOf("NA", "1234")
        val create = Create()
        val delivered = Delivered()
        val lost = Lost()
        val delayed = Delayed()
        val location = Location()
        val canceled = Canceled()
        val noteAdded = NoteAdded()
        val shipped = Shipped()
        TrackingServer.addShipment(shipment)
        arguments.add("hello!")

        assertFailsWith<IllegalArgumentException> {delivered.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {lost.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {canceled.performUpdate(arguments.toList())}

        arguments.add("hello!")

        assertFailsWith<IllegalArgumentException> {location.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {canceled.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {noteAdded.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {shipped.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {create.performUpdate(arguments.toList())}

        arguments.removeLast()
        arguments.removeLast()
        arguments.add("-1")
        arguments.add("hello again!")
        assertFailsWith<IllegalArgumentException> {create.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {delivered.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {lost.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {delayed.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {canceled.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {shipped.performUpdate(arguments.toList())}

    }

    @Test
    fun TestIncorrectNumberOfArguments()
    {

        val shipment = StandardShipment("1234", "created", mutableListOf(),  mutableListOf(), -1, "unknown", 12345678)
        val arguments: MutableList<String> = mutableListOf("NA", "1234")

        TrackingServer.addShipment(shipment)
        val create = Create()
        val delivered = Delivered()
        val lost = Lost()
        val delayed = Delayed()
        val location = Location()
        val canceled = Canceled()
        val noteAdded = NoteAdded()
        val shipped = Shipped()

        // To fail with 2
        assertFailsWith<IllegalArgumentException> {create.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {delivered.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {lost.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {canceled.performUpdate(arguments.toList())}




        // To fail with 3
        arguments.add("hello!")
        assertFailsWith<IllegalArgumentException> {create.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {location.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {delayed.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {noteAdded.performUpdate(arguments.toList())}
        assertFailsWith<IllegalArgumentException> {shipped.performUpdate(arguments.toList())}


    }
    @Test
    fun TestShipmentExistingOrNot()
    {
        val shipment = StandardShipment("1234", "created", mutableListOf(),  mutableListOf(), -1, "unknown", 12345678)

        TrackingServer.addShipment(shipment)
        val create = Create()
        val delivered = Delivered()
        val lost = Lost()
        val delayed = Delayed()
        val location = Location()
        val canceled = Canceled()
        val noteAdded = NoteAdded()
        val shipped = Shipped()
        // Shipment does not exist for create
        val arguments1: MutableList<String> = mutableListOf("NA", "12345", "hello....")
        assertFailsWith<IllegalArgumentException> {create.performUpdate(arguments1.toList())}

        // Attempting to update non-existing shipment

        arguments1.removeLast()
        arguments1.add("1234")
        // With 3 arguments
        assertFailsWith<IllegalArgumentException> {delivered.performUpdate(arguments1.toList())}
        assertFailsWith<IllegalArgumentException> {lost.performUpdate(arguments1.toList())}
        assertFailsWith<IllegalArgumentException> {canceled.performUpdate(arguments1.toList())}

        arguments1.add(":)")
        assertFailsWith<IllegalArgumentException> {create.performUpdate(arguments1.toList())}

        // With 4 arguments
        assertFailsWith<IllegalArgumentException> {delayed.performUpdate(arguments1.toList())}
        assertFailsWith<IllegalArgumentException> {shipped.performUpdate(arguments1.toList())}
        assertFailsWith<IllegalArgumentException> {noteAdded.performUpdate(arguments1.toList())}
        assertFailsWith<IllegalArgumentException> {location.performUpdate(arguments1.toList())}

    }

}