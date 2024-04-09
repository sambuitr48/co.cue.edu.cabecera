package repository.impl.order;

import connection.DataBaseConnection;
import models.Client;
import models.Employee;
import models.Order;
import repository.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryJDBCImpl implements Repository<Order> {
    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getInstance();
    }
    private Order createOrder(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setOrder_id(resultSet.getInt("order_id"));
        order.setClient(new Client(
                resultSet.getInt("client_id"),
                resultSet.getString("client_name"),
                resultSet.getDate("client_age")
        ));
        order.setEmployee(new Employee(
                resultSet.getInt("employee_id"),
                resultSet.getString("employee_name"),
                resultSet.getDate("employee_age")
        ));
        Date dbSqlDate = resultSet.getDate("purchase_date");
        if (dbSqlDate != null) {
            LocalDate fechaRegistro = dbSqlDate.toLocalDate();
            order.setPurchase_date(fechaRegistro.atStartOfDay()); // Convierte LocalDate a LocalDateTime al inicio del día
        } else {
            order.setPurchase_date(null);
        }
        return order;
    }

    @Override
    public List<Order> list() {
        List<Order> orderList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(
                     """ 
                         SELECT order., clients., employees.* 
                         FROM order 
                         INNER JOIN clients on order.client_id = clients.client_id
                         INNER JOIN employees on order.employee_id = employees.employee_id
                         """
             )) {
            while (resultSet.next()){
                Order order = createOrder(resultSet);
                orderList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }


    @Override
    public Order byId(int id) {
        Order order = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                         SELECT order., clients., employees.* 
                         FROM order 
                         INNER JOIN clients on order.client_id = clients.client_id
                         INNER JOIN employees on order.employee_id = employees.employee_id
                         WHERE order_id = ?
                            """
                )
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = createOrder(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public void save(Order order) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                            INSERT INTO order(íd_client,id_employee,purchase_date)
                            """
                )
        ) {
            preparedStatement.setInt(1, order.getClient().getClient_cedula());
            preparedStatement.setInt(2, order.getEmployee().getEmployee_id());
            LocalDateTime purchaseDate = order.getPurchase_date();
            preparedStatement.setDate(3, Date.valueOf(purchaseDate.toLocalDate()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                            DELETE FROM order 
                            WHERE order_id = ?
                            """
                )) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Order order) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                            UPDATE order SET íd_client = ? , id_employee = ? , purchase_date = ? 
                            WHERE order_id = ?
                            """
                )) {
            preparedStatement.setInt(1, order.getClient().getClient_cedula());
            preparedStatement.setInt(2, order.getEmployee().getEmployee_id());
            LocalDateTime purchaseDate = order.getPurchase_date();
            preparedStatement.setDate(3, Date.valueOf(purchaseDate.toLocalDate()));
            preparedStatement.setInt(4,order.getOrder_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order byName(String name) {
        return null;
    }

}
