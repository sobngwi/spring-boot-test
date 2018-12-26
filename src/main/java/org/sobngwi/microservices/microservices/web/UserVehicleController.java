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


import org.sobngwi.microservices.microservices.exceptions.UserNameNotFoundException;
import org.sobngwi.microservices.microservices.service.local.UserLocalVehicleServiceImpl;
import org.sobngwi.microservices.microservices.service.remote.VehicleDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class UserVehicleController {

	private static Logger log =  Logger.getLogger("UserVehicleController");
	private UserLocalVehicleServiceImpl userLocalVehicleServiceImpl;

	public UserVehicleController(UserLocalVehicleServiceImpl userLocalVehicleServiceImpl) {
		this.userLocalVehicleServiceImpl = userLocalVehicleServiceImpl;
	}

	@GetMapping(path = "/{username}/vehicle", produces = MediaType.TEXT_PLAIN_VALUE)
	public String getVehicleDetailsText(@PathVariable String username) {
		VehicleDetails details = this.userLocalVehicleServiceImpl.getVehicleDetails(username);
		return details.getMake() + " " + details.getModel();
	}

	@GetMapping(path = "/{username}/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
	public VehicleDetails VehicleDetailsJson(@PathVariable String username) {
		return this.userLocalVehicleServiceImpl.getVehicleDetails(username);
	}

	@GetMapping(path = "/{username}/vehicle.html", produces = MediaType.TEXT_HTML_VALUE)
	public String VehicleDetailsHtml(@PathVariable String username) {
		VehicleDetails details = this.userLocalVehicleServiceImpl.getVehicleDetails(username);
		String makeAndModel = details.getMake() + " " + details.getModel();
		return "<html><body><h1>" + makeAndModel + "</h1></body></html>";
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	private void handleUserNotFound(UserNameNotFoundException ex) {
		log.info("Exception user not found  : " + ex.getUsername());
	}

}
