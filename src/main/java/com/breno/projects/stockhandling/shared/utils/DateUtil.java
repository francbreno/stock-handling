package com.breno.projects.stockhandling.shared.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * An utility class for Date operations over the Java 8's Date API.
 * 
 * @author breno
 *
 */
public final class DateUtil {
	
	/*
	 * This class must not be instantiated.
	 */
	private DateUtil() {
        throw new AssertionError(DateUtil.class.getName() + " cannot be instantiated!");
    }
	
	/**
	 * Returns the current date.
	 * 
	 * @return	a {@link LocalDate LocalDate}
	 */
	public static LocalDate today() {
		return Instant.now().atOffset(ZoneOffset.UTC).toLocalDate();
	}
	
	/**
	 * Returns the first day of the last month.
	 * 
	 * @return	a {@link LocalDate LocalDate}
	 */
	public static LocalDate firstDayOfLastMonth() {
		return today().minusMonths(1).withDayOfMonth(1);
	}
	
	/**
	 * Returns the last day of the last month.
	 * 
	 * @return a {@link LocalDate LocalDate}
	 */
	public static LocalDate lastDayOfLastMonth() {
		LocalDate firstDayOfLastMonth = DateUtil.firstDayOfLastMonth();

		return firstDayOfLastMonth
				.withDayOfMonth(firstDayOfLastMonth.lengthOfMonth());
	}
	
	/**
	 * Checks if a date is between 2 others.
	 * 
	 * @param 	date
	 * 			the {@link LocalDate LocalDate} to check
	 * @param 	startDate
	 * 			the initial date of the interval
	 * @param 	endDate
	 * 			the last date of the interval
	 * @return
	 * 			{@code true} if date between the other two, {@code false} otherwise
	 */
	public static boolean isBetween(
			LocalDate date, LocalDate startDate, LocalDate endDate) {

		return (date.isAfter(startDate) || date.isEqual(startDate))
				&& (date.isBefore(endDate) || date.isEqual(endDate));
	}
	
	public static boolean isInTheLastMonth(Instant timestamp) {
		LocalDate date = timestamp.atOffset(ZoneOffset.UTC).toLocalDate();
		return isBetween(date, firstDayOfLastMonth(), lastDayOfLastMonth());
	}
	
	/**
	 * Checks if a {@link Instant Instant} is between the start of the day
	 * and the provided {@link Instant Instant}.
	 * 
	 * <p>This method will check converting the @{link Instant Instant}
	 * values to @{link LocalDateTime LocalDateTime}.
	 * 
	 * @param 	instant
	 * 			the {@ Instant Instant} to be checked
	 * @param 	now
	 * 			the last moment of the interval to check
	 * @return	{@code true} if instant is between the start of day (midnight),
	 * 			@{code false} otherwise.
	 */
	public static boolean isFromStartOfDayUntilNow(
			Instant instant, Instant now) {
		
		LocalDateTime time = instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
		LocalDateTime startOfDay = time.toLocalDate().atStartOfDay();
		LocalDateTime currentTime = now.atOffset(ZoneOffset.UTC).toLocalDateTime();
		
		return (time.isAfter(startOfDay) || time.isEqual(startOfDay))
				&& (time.isBefore(currentTime) || time.isEqual(currentTime));
	}
}
