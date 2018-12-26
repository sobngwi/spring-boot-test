package org.sobngwi.microservices.microservices.service.local;

import org.sobngwi.microservices.microservices.service.remote.VehicleDetails;

public interface UserLocalVehicleService {
    VehicleDetails getVehicleDetails(String username);
}
