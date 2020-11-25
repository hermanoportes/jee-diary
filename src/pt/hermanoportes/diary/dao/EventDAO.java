package pt.hermanoportes.diary.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import pt.hermanoportes.diary.connection.ConnectionFactory;
import pt.hermanoportes.diary.model.Contact;
import pt.hermanoportes.diary.model.Event;

public class EventDAO {
	
	private Connection conn;
	
	public EventDAO() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	public List<Event> findAll() {
		List<Event> eventList = new ArrayList<>();
		String sql = "SELECT * FROM event;";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
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
				List<Contact> contacts = new EventContactDAO().getContactsByEvent(rs.getLong("id"));
				event.setAttendants(contacts);				
				eventList.add(event);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return eventList;
	}
	
	public void add(Event event) {
		String sql = "INSERT INTO event (title, description, local, day, startAt, endAt) VALUES (?, ?, ?, ?, ?, ?);";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, event.getTitle());
			stmt.setString(2, event.getDescription());
			stmt.setString(3, event.getLocal());
			stmt.setDate(4, Date.valueOf(event.getDay()));
			stmt.setTime(5, Time.valueOf(event.getStartAt()));
			if(event.getEndAt() != null) {
				stmt.setTime(6, Time.valueOf(event.getEndAt()));
			} else {
				stmt.setTime(6, Time.valueOf(event.getStartAt()));
			}			
			stmt.execute();
			stmt.close();
			
			Long eventId = getUniqueEvent(event.getTitle(), event.getDay(), event.getStartAt());
			
			for(Contact contact : event.getAttendants()) {
				new EventContactDAO().addContactToEvent(eventId, contact.getId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}	
	
	public void update(Event event) {
		String sql = "UPDATE event SET title=?, description=?, local=?, day=?, startAt=?, endAt=? WHERE id=?;";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, event.getTitle());
			stmt.setString(2, event.getDescription());
			stmt.setString(3, event.getLocal());
			stmt.setDate(4, Date.valueOf(event.getDay()));
			stmt.setTime(5, Time.valueOf(event.getStartAt()));
			if(event.getEndAt() != null) {
				stmt.setTime(6, Time.valueOf(event.getEndAt()));
			} else {
				stmt.setTime(6, Time.valueOf(event.getStartAt()));
			}
			stmt.setLong(7, event.getId());
			stmt.execute();			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Event event) {
		String sql = "DELETE FROM event WHERE id=?;";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, event.getId());
			stmt.execute();
			stmt.close();
			
			new EventContactDAO().deleteEventAttendences(event.getId());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private Long getUniqueEvent(String title, LocalDate day, LocalTime startAt) {
		Long id = 0L;
		String sql = "SELECT id FROM event WHERE title=? AND day=? AND startAt=?;";
		
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, title);
			stmt.setDate(2, Date.valueOf(day));
			stmt.setTime(3, Time.valueOf(startAt));
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				id = rs.getLong("id");
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
}
