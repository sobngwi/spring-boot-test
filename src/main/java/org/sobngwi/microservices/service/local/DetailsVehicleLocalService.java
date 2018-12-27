package org.sobngwi.microservices.service.local;

import org.sobngwi.microservices.exceptions.VehicleIdentificationNumberNotFoundException;
import org.sobngwi.microservices.service.remote.VehicleDetails;

public interface DetailsVehicleLocalService {
    VehicleDetails getVehicleDetailsMock(String vin) throws VehicleIdentificationNumberNotFoundException;
}
