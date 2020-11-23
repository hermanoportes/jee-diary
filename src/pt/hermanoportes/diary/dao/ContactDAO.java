package pt.hermanoportes.diary.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pt.hermanoportes.diary.connection.ConnectionFactory;
import pt.hermanoportes.diary.model.Contact;

public class ContactDAO {
	
	private Connection conn;
	
	public ContactDAO() {
		this.conn = new ConnectionFactory().getConnection();
	}
	
	public List<Contact> findAll() {
		List<Contact> contacts = new ArrayList<>();
		String sql = "SELECT * FROM contact;";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Contact contact = new Contact();
				contact.setId(rs.getLong("id"));
				contact.setName(rs.getString("name"));
				contact.setEmail(rs.getString("email"));
				contact.setPhone(rs.getString("phone"));
				contact.setAddress(rs.getString("address"));
				contact.setBirthday(rs.getDate("birthday").toLocalDate());
				contacts.add(contact);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return contacts;
	}
	
	public List<Contact> findByName(String filter) {
		List<Contact> contacts = new ArrayList<>();
		String sql = "SELECT * FROM contact WHERE name LIKE ?;";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + filter + "%");
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				Contact contact = new Contact();
				contact.setId(rs.getLong("id"));
				contact.setName(rs.getString("name"));
				contact.setEmail(rs.getString("email"));
				contact.setPhone(rs.getString("phone"));
				contact.setAddress(rs.getString("address"));
				contact.setBirthday(rs.getDate("birthday").toLocalDate());
				contacts.add(contact);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return contacts;
	}
	
	public Contact getById(long id) {
		Contact contact = new Contact();
		String sql = "SELECT * FROM contact WHERE id=?;";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				contact.setId(rs.getLong("id"));
				contact.setName(rs.getString("name"));
				contact.setEmail(rs.getString("email"));
				contact.setPhone(rs.getString("phone"));
				contact.setAddress(rs.getString("address"));
				contact.setBirthday(rs.getDate("birthday").toLocalDate());
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contact;
	}
	
	public void add(Contact contact) {
		String sql = "INSERT INTO contact (name, email, phone, address, birthday) VALUES (?, ?, ?, ?, ?);";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, contact.getName());
			stmt.setString(2, contact.getEmail());
			stmt.setString(3, contact.getPhone());
			stmt.setString(4, contact.getAddress());
			stmt.setDate(5, Date.valueOf(contact.getBirthday()));
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Contact contact) {
		String sql = "UPDATE contact SET name=?, email=?, phone=?, address=?, birthday=? WHERE id=?;";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, contact.getName());
			stmt.setString(2, contact.getEmail());
			stmt.setString(3, contact.getPhone());
			stmt.setString(4, contact.getAddress());
			stmt.setDate(5, Date.valueOf(contact.getBirthday()));
			stmt.setLong(6, contact.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(Contact contact) {
		String sql = "DELETE FROM contact WHERE id=?;";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, contact.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
