/*
 * Copyright 2012-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sobngwi.microservices.microservices.service.local;


import org.sobngwi.microservices.microservices.exceptions.VehicleIdentificationNumberNotFoundException;
import org.sobngwi.microservices.microservices.service.remote.VehicleDetails;
import org.sobngwi.microservices.microservices.service.remote.VehicleDetailsRemoteService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.logging.Logger;

@Component
public class DetailsVehicleLocalServiceImpl implements DetailsVehicleLocalService {

	private static Logger log = Logger.getLogger("DetailsVehicleLocalServiceImpl");

	private final VehicleDetailsRemoteService vehicleDetailsRemoteService;

	public DetailsVehicleLocalServiceImpl(VehicleDetailsRemoteService vehicleDetailsRemoteService) {

		this.vehicleDetailsRemoteService = vehicleDetailsRemoteService;
	}

	@Override
	public VehicleDetails getVehicleDetailsMock(String vin) throws  VehicleIdentificationNumberNotFoundException {
		Assert.notNull(vin, "Username must not be null");

		log.info("Looking for vehicule details  " + vin);
		return new VehicleDetails("VW", "Tiguan");
	}
}
