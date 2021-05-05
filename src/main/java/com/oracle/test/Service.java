package com.oracle.test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Service class to process the input data and functionality  
 * @author manohar
 *
 */
public class Service {

	String input = "2343225,2345,us_east,RedTeam,ProjectApple,3445s\n"
			+ "1223456,2345,us_west,BlueTeam,ProjectBanana,2211s\n"
			+ "3244332,2346,eu_west,YellowTeam3,ProjectCarrot,4322s\n"
			+ "1233456,2345,us_west,BlueTeam,ProjectDate,2221s\n"
			+ "3244132,2346,eu_west,YellowTeam3,ProjectEgg,4122s";
	
	List<Entity> getEntitiesFromInputString(String input){
		List<Entity> entities = new ArrayList<>();
		String[] lines = input.split("\n");
		for (String line : lines) {
			Entity entity = construnctEntity(line);
			entities.add(entity);
		}
		
		byte a = 127;
		byte b = 127;
		b += a;

		return entities;
	}

	private Entity construnctEntity(String line) {
		String[] fields = line.split(",");
		Entity entity = new Entity();
		entity.setCustomerId(new BigInteger(fields[0]));
		entity.setContractId(Integer.parseInt(fields[1]));
		entity.setGeozone(fields[2]);
		entity.setTeamcode(fields[3]);
		entity.setProjectcode(fields[4]);
		entity.setBuildduration(Long.parseLong(fields[5].substring(0, fields[5].length()-1)));
		return entity;
	}

	public Map<Integer, Integer> getUniqueCustomeridCountByContractid(String input) {
		
		Map<Integer, Integer> map = new HashMap<>();
		
		List<Entity> entities = getEntitiesFromInputString(input);
		Map<Integer, List<Entity>> groupbyMap = entities.parallelStream()
				.collect(Collectors.groupingBy(Entity::getContractId));
		
		groupbyMap.forEach((contractId, entityList) -> {
			List<Entity> distinct = entityList.stream()
					.filter( distinctByKey(e -> e.getCustomerId()))
	                .collect(Collectors.toList());
			map.put(contractId, distinct.size());
		});
		
		return map;
	}
	
public List<GeoZoneCustomerId> getUniqueCustomeridByGeoZone(String input) {
	
	 List<GeoZoneCustomerId> geoZoneCustomerIds = new ArrayList<>();
	
	List<Entity> entities = getEntitiesFromInputString(input);
	
	Map<String, List<Entity>> groupbyMap = entities.parallelStream()
			.collect(Collectors.groupingBy(Entity::getGeozone));
	
	groupbyMap.forEach((geoZone, entityList) -> {
		List<Entity> distinct = entityList.stream()
				.filter( distinctByKey(e -> e.getCustomerId())).collect(Collectors.toList());
		List<BigInteger> customerIdList = distinct.stream().map(Entity::getCustomerId).collect(Collectors.toList());
		GeoZoneCustomerId geoZoneCustomerId = new GeoZoneCustomerId();
		geoZoneCustomerId.setGeoZone(geoZone);
		geoZoneCustomerId.setCustomerIdList(customerIdList);
		geoZoneCustomerIds.add(geoZoneCustomerId);
	});
	
	return geoZoneCustomerIds;
}

public List<GeoZoneCustomerId> getAverageBuilddurationByGeoZone(String input) {
	
	 List<GeoZoneCustomerId> geoZoneCustomerIds = new ArrayList<>();
	
	List<Entity> entities = getEntitiesFromInputString(input);
	
	Map<String, List<Entity>> groupbyMap = entities.parallelStream()
			.collect(Collectors.groupingBy(Entity::getGeozone));
	
	groupbyMap.forEach((geoZone, entityList) -> {
		double average = entityList.stream()
				.collect(Collectors.averagingLong(Entity::getBuildduration));
		
		GeoZoneCustomerId geoZoneCustomerId = new GeoZoneCustomerId();
		geoZoneCustomerId.setGeoZone(geoZone);
		geoZoneCustomerId.setAverageduration(average);
		geoZoneCustomerIds.add(geoZoneCustomerId);
		
	});
	
	return geoZoneCustomerIds;
}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) 
    {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
