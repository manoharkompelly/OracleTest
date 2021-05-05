package com.oracle.test;

import java.math.BigInteger;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Enity class to hold the properties
 * @author manohar
 *
 */
@Getter @Setter
@Data
@ToString @EqualsAndHashCode
public class Entity {

	BigInteger  customerId;
	Integer contractId;
	String geozone;
	String teamcode;
	String projectcode;
	long buildduration;
}
