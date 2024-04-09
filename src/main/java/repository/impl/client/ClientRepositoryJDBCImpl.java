package repository.impl.client;

import connection.DataBaseConnection;
import models.Client;
import repository.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryJDBCImpl implements Repository<Client> {
    private Connection getConnection() throws SQLException{
        return DataBaseConnection.getInstance();
    }
    private Client createClient(ResultSet resultSet) throws SQLException {
        Client client = new Client();
        client.setClient_cedula(resultSet.getInt("client_cedula"));
        client.setClient_name(resultSet.getString("client_name"));
        Date dbSqlDate = resultSet.getDate("client_age");
        return client;
    }
    @Override
    public List<Client> list() {
        List<Client> clientList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(
                     """ 
                         SELECT * FROM clients
                         """
             )) {
            while (resultSet.next()){
                Client client = createClient(resultSet);
                clientList.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientList;
    }

    @Override
    public Client byId(int id) {
        Client client = null;
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                            SELECT * FROM clients 
                            WHERE Client_cedula = ?
                            """
                )
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                client = createClient(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  client;
    }

    @Override
    public void save(Client client) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(
                """
                    INSERT INTO clients(client_cedula,client_name,client_age) 
                    VALUES (?,?,?)
                    """
        )){
            preparedStatement.setInt(1,client.getClient_cedula());
            preparedStatement.setString(2,client.getClient_name());
            preparedStatement.setDate(3, (Date) client.getClient_age());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                            DELETE FROM clients 
                            WHERE Client_cedula=?
                            """
                )) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Client client) {
        try (PreparedStatement preparedStatement = getConnection()
                .prepareStatement(
                        """
                            UPDATE clients 
                            SET Client_name = ?, Client_age = ? 
                            WHERE Client_cedula = ?
                            """
                )) {
            preparedStatement.setString(1, client.getClient_name());
            preparedStatement.setDate(2, (Date) client.getClient_age()); //Revisar
            preparedStatement.setInt(3,client.getClient_cedula());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Client byName(String name) {
        return null;
    }
}
