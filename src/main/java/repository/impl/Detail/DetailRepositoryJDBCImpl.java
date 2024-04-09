package repository.impl.Detail;

import connection.DataBaseConnection;
import models.Detail;
import models.Order;
import models.Toy;
import repository.Repository;
import repository.impl.order.OrderRepositoryJDBCImpl;
import repository.impl.toy.ToyRepositoryJDBCImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//En este sale severo error
public class DetailRepositoryJDBCImpl implements Repository<Detail> {
    private Connection getConnection() throws SQLException {
        return DataBaseConnection.getInstance();
    }
    private Detail createDetail(ResultSet resultSet) throws SQLException {
        Detail detail = new Detail();
        OrderRepositoryJDBCImpl orderRepositoryJDBC = new OrderRepositoryJDBCImpl();
        ToyRepositoryJDBCImpl toyRepositoryJDBC = new ToyRepositoryJDBCImpl();
        detail.setDetail_id(resultSet.getInt("detail_id"));
        int idOrder = resultSet.getInt("id_order");
        Order order = orderRepositoryJDBC.byId(idOrder);
        detail.setOrder(order);
        int idToy = resultSet.getInt("id_toy");
        Toy toy = toyRepositoryJDBC.byId(idToy);
        detail.setToy(toy);
        detail.setQuantity(resultSet.getInt("quantity"));
        detail.setUnit_price(resultSet.getDouble("unit_price"));
        detail.setTotal_Price(resultSet.getDouble("total_price"));

        return detail;
    }

    @Override
    public List<Detail> list() {
        List<Detail> detailList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(
                     """ 
                         SELECT detalle.,order.,toy.*
                         FROM detalle
                         INNER JOIN order ON detalle.id_order = order.order_id
                         INNER JOIN toy ON detalle.id_toy = toy.toy_id
                         """
             )) {
            while (resultSet.next()){
                Detail order = createDetail(resultSet);
                detailList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return detailList;
    }

    @Override
    public Detail byId(int id) {
        Detail detail = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                        SELECT detalle.,order.,toy.*
                        FROM detalle
                        INNER JOIN order ON detalle.id_order = order.order_id
                        INNER JOIN toy ON detalle.id_toy = toy.toy_id
                        WHERE detail_id = ?
                        """
                )
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                detail = createDetail(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return detail;
    }

    @Override
    public void save(Detail detail) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                            INSERT INTO detalle(id_order,id_toy,quantity,unit_price,total_price) 
                            VALUES (?,?,?,?,?)
                            """
                )
        ) {
            preparedStatement.setInt(1, detail.getOrder().getOrder_id());
            preparedStatement.setString(2, detail.getToy().getToy_name());
            preparedStatement.setInt(3, detail.getQuantity());
            preparedStatement.setDouble(4, detail.getUnit_price());
            preparedStatement.setDouble(5, detail.getQuantity()*detail.getUnit_price());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                            DELETE detalle 
                            WHERE detail_id=?
                            """
                )) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Detail detail) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                            UPDATE detalle 
                            SET id_order = ?, id_toy = ?, quantity = ?, unit_price = ?, total_price = ? 
                            WHERE detail_id = ?
                            """
                )) {
            preparedStatement.setInt(1, detail.getOrder().getOrder_id());
            preparedStatement.setString(2, detail.getToy().getToy_name());
            preparedStatement.setInt(3, detail.getQuantity());
            preparedStatement.setDouble(4, detail.getUnit_price());
            preparedStatement.setDouble(5, detail.getTotal_Price());
            preparedStatement.setInt(6, detail.getDetail_id());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Detail byName(String name) {
        return null;
    }

}
