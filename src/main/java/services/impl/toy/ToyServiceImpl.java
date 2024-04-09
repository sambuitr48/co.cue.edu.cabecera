package services.impl.toy;


import mapping.dto.ClientDTO;
import mapping.dto.EmployeeDTO;
import mapping.dto.ToyDTO;
import mapping.mappers.ClientMapper;
import mapping.mappers.EmployeeMapper;
import mapping.mappers.ToyMapper;
import models.*;
import repository.Repository;
import repository.impl.Detail.DetailRepositoryJDBCImpl;
import repository.impl.client.ClientRepositoryJDBCImpl;
import repository.impl.employee.EmployeeRepositoryJDBImpl;
import repository.impl.order.OrderRepositoryJDBCImpl;
import repository.impl.toy.ToyRepositoryJDBCImpl;
import services.Service;

import java.sql.SQLException;
import java.util.List;

public class ToyServiceImpl implements Service {
    private Repository<Toy> toyRepository;
    private Repository<Order> orderRepository;
    private Repository<Employee> employeeRepository;
    private Repository<Detail> detailRepository;
    private Repository<Client> clientRepository;

    public ToyServiceImpl() {
        this.toyRepository = new ToyRepositoryJDBCImpl();
        this.orderRepository = new OrderRepositoryJDBCImpl();
        this.employeeRepository = new EmployeeRepositoryJDBImpl();
        this.detailRepository = new DetailRepositoryJDBCImpl();
        this.clientRepository = new ClientRepositoryJDBCImpl();
    }

    @Override
    public void addToy(ToyDTO toyDTO) {
        toyRepository.save(ToyMapper.mapFromDto(toyDTO));
    }

    @Override
    public ToyDTO search(Integer id) throws SQLException {
        return ToyMapper.mapFromModel(toyRepository.byId(id));
    }

    @Override
    public void addEmployees(EmployeeDTO employeeDTO) {
        employeeRepository.save(EmployeeMapper.mapFromDto(employeeDTO));
    }

    @Override
    public void addClient(ClientDTO clientDTO) {
        clientRepository.save(ClientMapper.mapFromDTO(clientDTO));
    }

    @Override
    public List<ClientDTO> listClients() {
        return clientRepository.list()
                .stream()
                .map(ClientMapper::mapFromModel)
                .toList();
    }

    @Override
    public List<EmployeeDTO> listEmployees() {
        return employeeRepository.list()
                .stream()
                .map(EmployeeMapper::mapFromModel)
                .toList();
    }

    @Override
    public List<ToyDTO> listToys() {
        return toyRepository.list()
                .stream()
                .map(ToyMapper::mapFromModel)
                .toList();
    }

    @Override
    public ToyDTO searchByName(String name) throws SQLException {
        return ToyMapper.mapFromModel(toyRepository.byName(name));
    }
}