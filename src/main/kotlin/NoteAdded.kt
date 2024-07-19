class NoteAdded: Update {
    override fun performUpdate(arguments: List<String>) {
        if (arguments.size != 4)
        {
            throw IllegalArgumentException("Incorrect number of arguments...")
        }

        val shipment: Shipment? = TrackingServer.findShipment(arguments[1])

        if (shipment != null)
        {
            if (arguments[2].toLongOrNull() != null) {
                shipment.addNote(arguments[3])
            }
            else
            {
                throw IllegalArgumentException("Could not parse time stamp(s)")
            }

        }
        else
        {
            throw IllegalArgumentException("Trying to add a note to a non-existing shipment")
        }
    }


}