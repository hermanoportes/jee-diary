package pt.hermanoportes.diary.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pt.hermanoportes.diary.connection.ConnectionFactory;
import pt.hermanoportes.diary.model.Contact;
import pt.hermanoportes.diary.model.Event;

public class EventContactDAO {
	
	private Connection conn;
	
	public EventContactDAO() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	public List<Contact> getContactsByEvent(Long eventId) {
		List<Contact> contactList = new ArrayList<>();
		String sql = "SELECT contact.id, contact.name, contact.email, contact.phone, contact.address, contact.birthday "
				+ "FROM contact, event_contact "
				+ "WHERE event_contact.event_id=? "
				+ "AND contact.id=event_contact.contact_id;";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, eventId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Contact contact = new Contact();
				contact.setId(rs.getLong("id"));
				contact.setName(rs.getString("name"));
				contact.setEmail(rs.getString("email"));
				contact.setPhone(rs.getString("phone"));
				contact.setAddress(rs.getString("address"));
				contact.setBirthday(rs.getDate("birthday").toLocalDate());			
				contactList.add(contact);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return contactList;
	}
	
	public List<Event> getEventsByContact(Long contactId) {
		List<Event> eventList = new ArrayList<>();
		String sql = "SELECT event.id, event.title, event.description, event.local, event.day, event.startAt, event.endAt "
				+ "FROM event, event_contact "
				+ "WHERE event_contact.contact_id=? "
				+ "AND event.id=event_contact.event_id;";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, contactId);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Event event = new Event();
				event.setId(rs.getLong("id"));
				event.setTitle(rs.getString("title"));
				event.setDescription(rs.getString("description"));
				event.setLocal(rs.getString("local"));
				event.setDay(rs.getDate("day").toLocalDate());
				event.setStartAt(rs.getTime("startAt").toLocalTime());				
				event.setEndAt(rs.getTime("endAt").toLocalTime());
				event.setAttendants(new EventContactDAO().getContactsByEvent(rs.getLong("id")));				
				eventList.add(event);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return eventList;
	}
	
	public void addContactToEvent(Long eventId, Long contactId) {
		String sql = "INSERT INTO event_contact (event_id, contact_id) VALUES (?, ?);";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, eventId);
			stmt.setLong(2, contactId);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeContactFromEvent(Long eventId, Long contactId) {
		String sql = "DELETE FROM event_contact WHERE event_id=? AND contact_id=?;";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, eventId);
			stmt.setLong(2, contactId);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteEventAttendences(Long eventId) {
		String sql = "DELETE FROM event_contact WHERE event_id=?;";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, eventId);
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
