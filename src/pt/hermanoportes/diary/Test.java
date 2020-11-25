package pt.hermanoportes.diary;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import pt.hermanoportes.diary.dao.ContactDAO;
import pt.hermanoportes.diary.dao.EventContactDAO;
import pt.hermanoportes.diary.dao.EventDAO;
import pt.hermanoportes.diary.model.Contact;
import pt.hermanoportes.diary.model.Event;

public class Test {
	
	public static void main(String[] args) {
		new Test().removeContactFromEvent();
	}
	
	void addEvent() {
		Event event = new Event();
		event.setTitle("Terceiro Evento");
		event.setDescription("Novo evento");
		event.setLocal("Casa");
		event.setDay(LocalDate.of(2020, 11, 26));
		event.setStartAt(LocalTime.of(8, 30));
		event.setEndAt(LocalTime.of(15, 00));
		event.getAttendants().add(new ContactDAO().getById(12));
		event.getAttendants().add(new ContactDAO().getById(13));
		event.getAttendants().add(new ContactDAO().getById(14));
		new EventDAO().add(event);
	}
	
	void updateEvent() {
		Event event = new Event();
		event.setId(15L);
		event.setTitle("Evento alterado");
		event.setDescription("Evento alterado");
		event.setLocal("Casa");
		event.setDay(LocalDate.of(2020, 12, 01));
		event.setStartAt(LocalTime.of(12, 00));
		event.setEndAt(LocalTime.of(15, 00));
		event.getAttendants().add(new ContactDAO().getById(12));
		event.getAttendants().add(new ContactDAO().getById(13));
		event.getAttendants().add(new ContactDAO().getById(14));
		new EventDAO().update(event);
	}
	
	void deleteEvent() {
		Event event = new Event();
		event.setId(17L);
		new EventDAO().delete(event);
	}
	
	void findEvents() {
		List<Event> eventList = new EventDAO().findAll();
		for(Event event : eventList) {
			System.out.println(event.getId() + "- " + event.getTitle());
			System.out.println(event.getDescription());
			System.out.println(event.getLocal());
			System.out.println(event.getDay());
			System.out.println(event.getStartAt());
			System.out.println(event.getEndAt());
			getContactsByEvent(event.getId());
		}
	}
	
	void addContactToEvent() {
		new EventContactDAO().addContactToEvent(13L, 15L);
	}
	
	void removeContactFromEvent() {
		new EventContactDAO().removeContactFromEvent(15L, 12L);
	}
	
	void getContactsByEvent(Long eventId) {
		List<Contact> contactList =  new EventContactDAO().getContactsByEvent(eventId);
		for(Contact contact : contactList) {
			System.out.println(contact.getId() + "- " + contact.getName());
		}
	}
	
	void getEventByContact(Long contactId) {
		List<Event> eventList =  new EventContactDAO().getEventsByContact(contactId);
		for(Event event : eventList) {
			System.out.println(event.getId() + "- " + event.getTitle());
		}
	}
}
