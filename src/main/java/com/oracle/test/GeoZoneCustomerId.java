package com.oracle.test;

import java.math.BigInteger;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Geo Zone target object to hold the required properties
 * @author manohar
 *
 */
@Data
@Getter @Setter
@EqualsAndHashCode @NoArgsConstructor @AllArgsConstructor
public class GeoZoneCustomerId {

	String geoZone;
	List<BigInteger>  customerIdList;
	double averageduration;
}
