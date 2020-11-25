package pt.hermanoportes.diary.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
	
	private Long id;
	private String title;
	private String description;
	private String local;
	private LocalDate day;
	private LocalTime startAt;
	private LocalTime endAt;
	
	private List<Contact> attendants = new ArrayList<Contact>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	
	public LocalDate getDay() {
		return day;
	}
	public void setDay(LocalDate day) {
		this.day = day;
	}
	
	public LocalTime getStartAt() {
		return startAt;
	}
	public void setStartAt(LocalTime startAt) {
		this.startAt = startAt;
	}
	public LocalTime getEndAt() {
		return endAt;
	}
	public void setEndAt(LocalTime endAt) {
		this.endAt = endAt;
	}
	public List<Contact> getAttendants() {
		return attendants;
	}
	public void setAttendants(List<Contact> attendants) {
		this.attendants = attendants;
	}
}
