package repository.impl.employee;

import connection.DataBaseConnection;
import models.Employee;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryJDBImpl implements Repository<Employee> {
    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getInstance();
    }
    private Employee createEmployee(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployee_id(resultSet.getInt("employee_id"));
        employee.setEmployee_name(resultSet.getString("employee_name"));
        Date dbSqlDate = resultSet.getDate("employee_age");
        return employee;
    }
    @Override
    public List<Employee> list() {
        List<Employee> employeeList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(
                     """ 
                         SELECT * FROM employees
                         """
             )) {
            while (resultSet.next()){
                Employee employee = createEmployee(resultSet);
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }


    @Override
    public Employee byId (int id) {
        Employee employee = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                            SELECT * FROM employees WHERE employee_id = ?
                            """
                )
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = createEmployee(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employee;
    }

    @Override
    public void save(Employee employee) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                            INSERT INTO employees (employee_name=? , employee_age=?) 
                            VALUES (?,?)
                            """
                )
        ) {
            preparedStatement.setString(1, employee.getEmployee_name());
            preparedStatement.setDate(2, (Date) employee.getEmployee_age()); //Revisar
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                            DELETE FROM employees 
                            WHERE employee_id = ?
                            """
                )) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Employee employee) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                            UPDATE employees 
                            SET employee_name = ? , employee_age = ? 
                            WHERE employee_id = ?
                            """
                )) {
            preparedStatement.setString(1, employee.getEmployee_name());
            preparedStatement.setDate(2, (Date) employee.getEmployee_age()); //Revisar
            preparedStatement.setInt(3,employee.getEmployee_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee byName(String name) {
        return null;
    }
}
