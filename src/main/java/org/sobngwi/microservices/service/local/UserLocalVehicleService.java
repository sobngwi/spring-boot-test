package org.sobngwi.microservices.service.local;

import org.sobngwi.microservices.service.remote.VehicleDetails;

public interface UserLocalVehicleService {
    VehicleDetails getVehicleDetails(String username);
}
