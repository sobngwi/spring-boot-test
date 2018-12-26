package org.sobngwi.microservices.microservices.service.local;

import org.sobngwi.microservices.microservices.exceptions.VehicleIdentificationNumberNotFoundException;
import org.sobngwi.microservices.microservices.service.remote.VehicleDetails;

public interface DetailsVehicleLocalService {
    VehicleDetails getVehicleDetailsMock(String vin) throws VehicleIdentificationNumberNotFoundException;
}
