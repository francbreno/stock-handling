package com.breno.projects.stockhandling.shared.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DateUtilTests {

	@Test
	public void shouldReturnTheFirstDayOfLastMonth() {
		LocalDate today = DateUtil.today();
		LocalDate date = DateUtil.firstDayOfLastMonth();

		assertThat(date.getDayOfMonth()).isEqualTo(1);
		assertThat(date.getMonthValue()).isEqualTo(today.getMonthValue() - 1);
	}
	
	@Test
	public void shouldReturnCurrentDate() {
		LocalDate today = DateUtil.today();
		
		assertThat(today).isEqualTo(LocalDate.now());
	}
	
	@Test
	public void shouldReturnLastDayOfLastMonth() {
		LocalDate lastDayOfLastmonth = DateUtil.today().withDayOfMonth(1).minusDays(1);
		LocalDate date = DateUtil.lastDayOfLastMonth();
		
		assertThat(date).isEqualTo(lastDayOfLastmonth);
	}

	@Test
	public void shouldReturnTrueIfDateInTheLastMonth() {
		LocalDate date = LocalDate.now().minusMonths(1);
		assertThat(DateUtil.isInTheLastMonth(date)).isTrue();
	}
	
	@Test
	public void shouldReturnFalseIfDateNotInTheLastMonth() {
		LocalDate date = LocalDate.now();
		assertThat(DateUtil.isInTheLastMonth(date)).isFalse();
	}
	
	@Test
	public void shouldReturnTrueIfDateBetweenTwoOthers() {
		LocalDate date = LocalDate.now();
		LocalDate firstDate = LocalDate.now().minusDays(2);
		LocalDate secondDate = LocalDate.now().plusDays(2);
		
		assertThat(DateUtil.isBetween(date, firstDate, secondDate)).isTrue();
	}
	
	@Test
	public void shouldReturnTrueIfDateBetweenTwoOthersWhenDateEqualsFirstOne() {
		LocalDate date = LocalDate.now();
		LocalDate firstDate = LocalDate.from(date);
		LocalDate secondDate = LocalDate.now().plusDays(2);
		
		assertThat(DateUtil.isBetween(date, firstDate, secondDate)).isTrue();
	}
	
	@Test
	public void shouldReturnTrueIfDateBetweenTwoOthersWhenDateEqualsSecondOne() {
		LocalDate date = LocalDate.now();
		LocalDate firstDate = LocalDate.now().minusDays(2);
		LocalDate secondDate = LocalDate.from(date);
		
		assertThat(DateUtil.isBetween(date, firstDate, secondDate)).isTrue();
	}
}
