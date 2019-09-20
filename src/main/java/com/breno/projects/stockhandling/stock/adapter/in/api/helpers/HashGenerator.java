package com.breno.projects.stockhandling.stock.adapter.in.api.helpers;

import static java.util.stream.Collectors.joining;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.IntStream;

/**
 * This class is responsible for generating hash values for an
 * input according to an hashing algorithm.
 * 
 * <p>This class is a <i>wrapper</i> around {@link MessageDigest MessageDigest}.
 * 
 * <p>The hashing algorithm needs to be provided to the class
 * constructor and cannot change anymore. This is necessary
 * for consistency.
 * 
 * @author breno
 *
 */
public class HashGenerator {
	public static final String SHA_256 = "SHA-256";

	private final MessageDigest digest;

	/**
	 * Returns a {@link HashGenerator HashGenerator} instance
	 * capable of generating hash values based on the provided algorithm.
	 * 
	 * @param	algorithm
	 * 			to be used to generate a hash.
	 * @throws 	NoSuchAlgorithmException
	 * 			is thrown if the provided algorithm doesn't exist.
	 */
	public HashGenerator(String algorithm) throws NoSuchAlgorithmException {
		digest = MessageDigest.getInstance(algorithm);
	}
	
	/**
	 * Generates a hash for the provided {@link String String} using the algorithm
	 * provided to the constructor.
	 * 
	 * @param 	stringToHash
	 * 			The {@link String String} to be hashed
	 * @return	A {@link String String} representing the hash value
	 */
	public String generate(String stringToHash) {
		Objects.nonNull(stringToHash);

		byte[] hashBytes = digest.digest(stringToHash.getBytes());

		return IntStream
			.range(0, hashBytes.length)
			.mapToObj(createByteArrayToStringMapper(hashBytes))
			.collect(joining());
	}

	/*
	 * A Higher order method returning a mapper function to
	 * convert from byte to String.
	 */
	private IntFunction<? extends String> createByteArrayToStringMapper(byte[] bytes) {
		// TODO - explain the bit 'and'
		return i -> String.format("%02X", 0xFF & bytes[i]);
	}
}
