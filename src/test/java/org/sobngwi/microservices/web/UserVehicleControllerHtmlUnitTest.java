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

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sobngwi.microservices.service.local.UserLocalVehicleService;
import org.sobngwi.microservices.service.local.UserLocalVehicleServiceImpl;
import org.sobngwi.microservices.service.remote.VehicleDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * HtmlUnit based tests for {@link UserVehicleController}.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserVehicleController.class)
public class UserVehicleControllerHtmlUnitTest {

	@Autowired
	private WebClient webClient;

	@MockBean
	private UserLocalVehicleServiceImpl userVehicleService;

	@Test
	public void getVehicleWhenRequestingTextShouldReturnMakeAndModel() throws Exception {
		given(this.userVehicleService.getVehicleDetails("donald"))
				.willReturn(new VehicleDetails("Honda", "Civic"));

		HtmlPage page = webClient.getPage("/donald/vehicle.html");

		assertThat(page.getBody().getTextContent()).isEqualTo("Honda Civic");
	}

}
