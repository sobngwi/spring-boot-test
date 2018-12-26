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

package org.sobngwi.microservices.microservices.web;


import org.sobngwi.microservices.microservices.exceptions.VehicleIdentificationNumberNotFoundException;
import org.sobngwi.microservices.microservices.service.local.DetailsVehicleLocalService;
import org.sobngwi.microservices.microservices.service.local.DetailsVehicleLocalServiceImpl;
import org.sobngwi.microservices.microservices.service.remote.VehicleDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class DetailsVehicleController {

	private static Logger log =  Logger.getLogger("DetailsVehicleController");
	private DetailsVehicleLocalServiceImpl detailsVehicleLocalService;

	public DetailsVehicleController(DetailsVehicleLocalServiceImpl detailsVehicleLocalService) {
		this.detailsVehicleLocalService = detailsVehicleLocalService;
	}

	@GetMapping(path = "/vs/vehicle/{vin}/details", produces = MediaType.APPLICATION_JSON_VALUE)
	public VehicleDetails VehicleDetailsJsonByVin(@PathVariable String vin) {
		log.info("[VehicleDetailsJsonByVin]Looking for vin = " + vin );
		return this.detailsVehicleLocalService.getVehicleDetailsMock(vin);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void handleVinNotFound(VehicleIdentificationNumberNotFoundException ex) {
		log.info("Exception vin not found  : " + ex.getVehicleIdentificationNumber());
	}


}
