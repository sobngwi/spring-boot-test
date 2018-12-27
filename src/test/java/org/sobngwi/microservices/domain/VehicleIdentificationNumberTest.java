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

package org.sobngwi.microservices.domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link VehicleIdentificationNumber}.
 */
public class VehicleIdentificationNumberTest {

	SoftAssertions softly;
	private static final String SAMPLE_VIN = "01234567890123456";

	@Before
	public void setUp() {
		softly = new SoftAssertions();
	}

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void createWhenVinIsNullShouldThrowException()  {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("VIN must not be null");
		new VehicleIdentificationNumber(null);
	}

	@Test
	public void createWhenVinIsMoreThan17CharsShouldThrowException() {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("VIN must be exactly 17 characters");
		new VehicleIdentificationNumber("012345678901234567");
	}

	@Test
	public void createWhenVinIsLessThan17CharsShouldThrowException() {
		this.thrown.expect(IllegalArgumentException.class);
		this.thrown.expectMessage("VIN must be exactly 17 characters");
		new VehicleIdentificationNumber("0123456789012345");
	}

	@Test
	public void toStringShouldReturnVin(){
		VehicleIdentificationNumber vin = new VehicleIdentificationNumber(SAMPLE_VIN);
		assertThat(vin.toString()).isEqualTo(SAMPLE_VIN);
	}

	@Test
	public void equalsAndHashCodeShouldBeBasedOnVin(){
		VehicleIdentificationNumber vin1 = new VehicleIdentificationNumber(SAMPLE_VIN);
		VehicleIdentificationNumber vin2 = new VehicleIdentificationNumber(SAMPLE_VIN);
		VehicleIdentificationNumber vin3 = new VehicleIdentificationNumber(
				"00000000000000000");

		softly.assertThat(vin1).isNotNull();
		softly.assertThat(vin2).isNotNull();
		softly.assertThat(vin1.hashCode()).isEqualTo(vin2.hashCode()).isNotEqualTo(vin3.hashCode());
		softly.assertThat(vin1).isEqualTo(vin2).isNotEqualTo(vin3);
		softly.assertAll();

	}

}
