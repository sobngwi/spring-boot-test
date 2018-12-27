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

package org.sobngwi.microservices.service;


import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.sobngwi.microservices.domain.VehicleIdentificationNumber;
import org.sobngwi.microservices.exceptions.VehicleIdentificationNumberNotFoundException;
import org.sobngwi.microservices.service.remote.RemoteVehicleDetailsRemoteService;
import org.sobngwi.microservices.service.remote.RemoteVehicleDetailsServiceProperties;
import org.sobngwi.microservices.service.remote.VehicleDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

/**
 * Tests for {@link RemoteVehicleDetailsRemoteService}.
 */
@RunWith(SpringRunner.class)
@RestClientTest({ RemoteVehicleDetailsRemoteService.class,
		RemoteVehicleDetailsServiceProperties.class })
@TestPropertySource(properties = "vehicle.service.root-url=http://example.com/")
public class RemoteVehicleDetailsServiceTest {

	private static final String VIN = "01234567890123456";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private MockRestServiceServer server;

	@Autowired
	private RemoteVehicleDetailsRemoteService service;

	@Test
	public void getVehicleDetailsWhenVinIsNullShouldThrowException(){
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("VIN must not be null");

		service.getVehicleDetails(null);
	}

	@Test
	public void getVehicleDetailsWhenResultIsSuccessShouldReturnDetails() {
		server.expect(requestTo("http://example.com/vehicle/" + VIN + "/details"))
				.andRespond(withSuccess(getClassPathResource("vehicledetails.json"),
						MediaType.APPLICATION_JSON));

		VehicleDetails details = this.service
				.getVehicleDetails(new VehicleIdentificationNumber(VIN));

		assertThat(details.getMake()).isEqualTo("Honda");
		assertThat(details.getModel()).isEqualTo("Civic");
	}

	@Test
	public void getVehicleDetailsWhenResultIsNotFoundShouldThrowException() {
		this.server.expect(requestTo("http://example.com/vehicle/" + VIN + "/details"))
				.andRespond(withStatus(HttpStatus.NOT_FOUND));

		thrown.expect(VehicleIdentificationNumberNotFoundException.class);

		service.getVehicleDetails(new VehicleIdentificationNumber(VIN));
	}

	@Test
	public void getVehicleDetailsWhenResultIServerErrorShouldThrowException() {
		server.expect(requestTo("http://example.com/vehicle/" + VIN + "/details"))
				.andRespond(withServerError());
		thrown.expect(HttpServerErrorException.class);

		service.getVehicleDetails(new VehicleIdentificationNumber(VIN));
	}

	private ClassPathResource getClassPathResource(String path) {
		return new ClassPathResource(path, getClass());
	}

}
