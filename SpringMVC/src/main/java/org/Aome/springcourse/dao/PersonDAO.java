package org.Aome.springcourse.dao;

import org.Aome.springcourse.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT = 0;

    private static final String URL = "jdbc:postgresql://localhost:5432/first-db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "12345678";

    private static final Connection connection;

    static {
        try{
            Class.forName("org.postgresql.Driver");
        }catch (ClassNotFoundException classNotFoundException){
            System.out.println(classNotFoundException.getMessage());
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Person show(int id) {
        Person person;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Person WHERE id=?");
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            resultSet.next();

            person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return person;
    }

    public List<Person> showAll() {
        List<Person> people = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Person");

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Person person =new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));

                people.add(person);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return people;
    }

    public void save(Person person) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Person VALUES(?, ?, ?, ?)");

            statement.setInt(1, ++PEOPLE_COUNT);
            statement.setString(2, person.getName());
            statement.setInt(3, person.getAge());
            statement.setString(4, person.getEmail());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, Person person) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Person SET name=?, age=?, email=? WHERE id=?");

            statement.setString(1, person.getName());
            statement.setInt(2, person.getAge());
            statement.setString(3, person.getEmail());
            statement.setInt(4, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Person WHERE id=?");

            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
