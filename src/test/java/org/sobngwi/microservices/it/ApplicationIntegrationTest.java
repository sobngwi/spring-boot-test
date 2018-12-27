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

package org.sobngwi.microservices.it;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sobngwi.microservices.Application;
import org.sobngwi.microservices.domain.VehicleIdentificationNumber;
import org.sobngwi.microservices.service.remote.RemoteVehicleDetailsRemoteService;
import org.sobngwi.microservices.service.remote.VehicleDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Integration test for {@link Application} starting on a random port.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ApplicationIntegrationTest {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber(
			"01234567890123456");

	@Autowired
	private TestRestTemplate restTemplate;

	//@SpyBean
	@MockBean
	private RemoteVehicleDetailsRemoteService vehicleDetailsService;

	@Before
	public void setup() {
		given(this.vehicleDetailsService.getVehicleDetails(VIN))
				.willReturn(new VehicleDetails("Honda", "Civic"));
	}

	@Test
	public void testReturnTypeVehicleDetails() {
		ResponseEntity<VehicleDetails> response = this.restTemplate
				.getForEntity("/{username}/vehicle", VehicleDetails.class, "mickey");

		assertThat(response).isNotNull();
		assertThat(response.getHeaders()).containsValue(Arrays.asList("application/json;charset=UTF-8"));
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody().getMake()).isEqualTo("Honda");
		assertThat(response.getBody().getModel()).isEqualTo("Civic");

	}

	@Test
	public void testReturnTypeString() {
		ResponseEntity<String> response = this.restTemplate
				.getForEntity("/{username}/vehicle", String.class, "mickey");
		assertThat(response).isNotNull();
		assertThat(response.getHeaders()).containsValue(Arrays.asList("text/plain;charset=UTF-8"));
		assertThat(response.getBody()).isNotNull();
		assertThat(response.getBody()).contains("Honda");
		assertThat(response.getBody()).contains("Civic");
	}
}
