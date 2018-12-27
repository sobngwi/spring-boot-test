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

package org.sobngwi.microservices.service.remote;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sobngwi.microservices.domain.VehicleIdentificationNumber;
import org.sobngwi.microservices.exceptions.VehicleIdentificationNumberNotFoundException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class RemoteVehicleDetailsRemoteService implements VehicleDetailsRemoteService {

	private static final Logger logger = LoggerFactory
			.getLogger(RemoteVehicleDetailsRemoteService.class);

	private final RemoteVehicleDetailsServiceProperties properties;

	private final RestTemplate restTemplate;

	public RemoteVehicleDetailsRemoteService(RemoteVehicleDetailsServiceProperties properties,
											 RestTemplateBuilder restTemplateBuilder) {
		this.properties = properties;
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public VehicleDetails getVehicleDetails(VehicleIdentificationNumber vin)
			throws VehicleIdentificationNumberNotFoundException {
		Assert.notNull(vin, "VIN must not be null");
		String url = this.properties.getRootUrl() + "vehicle/" + vin.toString() + "/details";
		logger.info("Retrieving vehicle data for: " + vin.toString() + " from: " + url);
		try {
			return
			 this.restTemplate.getForObject(url, VehicleDetails.class, vin);
		}
		catch (HttpStatusCodeException ex) {
			if (HttpStatus.NOT_FOUND.equals(ex.getStatusCode())) {
				throw new VehicleIdentificationNumberNotFoundException(vin, ex);
			}
			throw ex;
		}
	}

}
