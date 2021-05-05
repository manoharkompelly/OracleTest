package com.example.springboot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.oracle.test.GeoZoneCustomerId;
import com.oracle.test.Service;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ServiceTest {

	
	String input;
	Service service;
	
	@BeforeEach
	public void setup() {
		input = "2343225,2345,us_east,RedTeam,ProjectApple,3445s\n"
				+ "1223456,2345,us_west,BlueTeam,ProjectBanana,2211s\n"
				+ "3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s\n"
				+ "1233456,2345,us_west,BlueTeam,ProjectDate,2221s\n"
				+ "3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s";
		service = new Service();
	}
	
	
	@Test
	public void getUniqueCustomeridCountByContractidTest() {
		Map<Integer, Integer> map = service.getUniqueCustomeridCountByContractid(input);
		assertThat(map).isNotNull();
	}
	
	@Test
	public void getUniqueCustomeridCountByGeoZoneTest() {
		List<GeoZoneCustomerId> geoZoneCustomerIds = service.getUniqueCustomeridByGeoZone(input);
		assertThat(geoZoneCustomerIds).isNotNull();
		log.info("Geo Zone | Unique CustomerIds count");
		for (GeoZoneCustomerId geoZoneCustomerId : geoZoneCustomerIds) {
			log.info("{} | {}", geoZoneCustomerId.getGeoZone(), geoZoneCustomerId.getCustomerIdList().size());
		}
		
	}
	
	@Test
	public void getUniqueCustomeridByGeoZoneTest() {
		List<GeoZoneCustomerId> geoZoneCustomerIds = service.getUniqueCustomeridByGeoZone(input);
		assertThat(geoZoneCustomerIds).isNotNull();
		log.info("Geo Zone | CustomerIds");
		for (GeoZoneCustomerId geoZoneCustomerId : geoZoneCustomerIds) {
			log.info("{} | {}", geoZoneCustomerId.getGeoZone(), geoZoneCustomerId.getCustomerIdList());
		}
		
	}
	
	@Test
	public void getAverageBuilddurationByGeoZoneTest() {
		List<GeoZoneCustomerId> geoZoneCustomerIds = service.getAverageBuilddurationByGeoZone(input);
		assertThat(geoZoneCustomerIds).isNotNull();
		log.info("Geo Zone | Average Build duration");
		for (GeoZoneCustomerId geoZoneCustomerId : geoZoneCustomerIds) {
			log.info("{} | {}", geoZoneCustomerId.getGeoZone(), geoZoneCustomerId.getAverageduration());
		}
	}
}
