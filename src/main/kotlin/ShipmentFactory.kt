fun createShipment(arguments: List<String>) : Shipment
{
    if (arguments.size == 4)
    {
        if (arguments[2] == "bulk")
        {
            return BulkShipment(arguments[1], "created", mutableListOf(), mutableListOf(), -1, "NA", arguments[3].toLong())

        }
        else if (arguments[2] == "standard")
        {
            return StandardShipment(arguments[1], "created", mutableListOf(), mutableListOf(), -1, "NA", arguments[3].toLong())

        }
        else if (arguments[2] == "overnight")
        {
            return OvernightShipment(arguments[1], "created", mutableListOf(), mutableListOf(), -1, "NA", arguments[3].toLong())

        }
        else if (arguments[2] == "express")
        {
            return ExpressShipment(arguments[1], "created", mutableListOf(), mutableListOf(), -1, "NA", arguments[3].toLong())

        }

        else
        {
            throw IllegalArgumentException("Shipment of that type does not exist!")
        }
    }
    else
    {
        throw IllegalArgumentException("Tried to create shipment, but could not parse which type!")
    }

}