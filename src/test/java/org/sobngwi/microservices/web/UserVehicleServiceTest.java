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

package org.sobngwi.microservices.web;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sobngwi.microservices.domain.User;
import org.sobngwi.microservices.domain.UserRepository;
import org.sobngwi.microservices.domain.VehicleIdentificationNumber;
import org.sobngwi.microservices.exceptions.UserNameNotFoundException;
import org.sobngwi.microservices.service.local.UserLocalVehicleService;
import org.sobngwi.microservices.service.local.UserLocalVehicleServiceImpl;
import org.sobngwi.microservices.service.remote.VehicleDetails;
import org.sobngwi.microservices.service.remote.VehicleDetailsRemoteService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

/**
 * Tests for {@link UserLocalVehicleService}.
 */

public class UserVehicleServiceTest {

	private static final VehicleIdentificationNumber VIN = new VehicleIdentificationNumber(
			"01234567890123456");
	VehicleDetails expectedVehicleDetail, actualVehicleDetail;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private VehicleDetailsRemoteService vehicleDetailsService;

	@Mock
	private UserRepository userRepository;

	private UserLocalVehicleService service;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new UserLocalVehicleServiceImpl(this.userRepository,
				this.vehicleDetailsService);
		expectedVehicleDetail = new VehicleDetails("Honda", "Civic");
	}

	@Test
	public void assertThatMocksHaveBeenCreated() {
		assertThat(vehicleDetailsService).isNotNull();
		assertThat(userRepository).isNotNull();
	}

	@Test
	public void getVehicleDetailsWhenUsernameIsNullShouldThrowException() {
		thrown.expect(IllegalArgumentException.class);
		thrown.expectMessage("Username must not be null");

		service.getVehicleDetails(null);
	}

	@Test
	public void getVehicleDetailsWhenUsernameNotFoundShouldThrowException() {
		thrown.expect(UserNameNotFoundException.class);
		thrown.expectMessage("donald");

		given(userRepository.findByUsername(anyString())).willReturn(null);

		service.getVehicleDetails("donald");

	}

	@Test
	public void getVehicleDetailsShouldReturnMakeAndModel() {
		given(userRepository.findByUsername("donald"))
				.willReturn(new User("donald", VIN));
		given(vehicleDetailsService.getVehicleDetails(VIN)).willReturn(expectedVehicleDetail);

		actualVehicleDetail = service.getVehicleDetails("donald");

		assertThat(actualVehicleDetail).isNotNull();
		assertThat(actualVehicleDetail).isEqualTo(expectedVehicleDetail);
	}

}
