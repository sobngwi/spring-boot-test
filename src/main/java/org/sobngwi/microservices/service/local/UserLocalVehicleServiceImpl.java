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

package org.sobngwi.microservices.service.local;


import org.sobngwi.microservices.domain.User;
import org.sobngwi.microservices.domain.UserRepository;
import org.sobngwi.microservices.exceptions.UserNameNotFoundException;
import org.sobngwi.microservices.service.remote.VehicleDetails;
import org.sobngwi.microservices.service.remote.VehicleDetailsRemoteService;
import org.sobngwi.microservices.exceptions.VehicleIdentificationNumberNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.logging.Logger;

@Component
public class UserLocalVehicleServiceImpl implements UserLocalVehicleService {

	private static Logger log = Logger.getLogger("UserVehicleService");

	private final UserRepository userRepository;

	private final VehicleDetailsRemoteService vehicleDetailsRemoteService;

	public UserLocalVehicleServiceImpl(UserRepository userRepository,
									   VehicleDetailsRemoteService vehicleDetailsRemoteService) {
		this.userRepository = userRepository;
		this.vehicleDetailsRemoteService = vehicleDetailsRemoteService;
	}


	@Override
	public VehicleDetails getVehicleDetails(String username)
			throws UserNameNotFoundException,
			VehicleIdentificationNumberNotFoundException {
		Assert.notNull(username, "Username must not be null");
		log.fine("Looking for user  " + username);
		User user = this.userRepository.findByUsername(username);
		if (user == null) {
			throw new UserNameNotFoundException(username);
		}
		return this.vehicleDetailsRemoteService.getVehicleDetails(user.getVin());
	}

}
