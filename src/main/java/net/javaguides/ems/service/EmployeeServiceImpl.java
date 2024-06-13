package net.javaguides.ems.service;

import lombok.AllArgsConstructor;
import net.javaguides.ems.dto.EmployeeDto;
import net.javaguides.ems.entity.Employee;
import net.javaguides.ems.exception.ResourceNotFoudException;
import net.javaguides.ems.mapper.EmployeeMapper;
import net.javaguides.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor

public class EmployeeServiceImpl implements EmployeeService {


    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {

        Employee employee= EmployeeMapper.mapToEmployee(employeeDto);
       Employee savedEmployee= employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(savedEmployee);
    }

    @Override
    public EmployeeDto getEmployeeId(Long employeId) {
        Employee employee=employeeRepository.findById(employeId)
                .orElseThrow(()->new ResourceNotFoudException("Employee is not exist with give id "+employeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployee() {
       List<Employee> employees= employeeRepository.findAll();
        return employees.stream().map((employee)->EmployeeMapper.mapToEmployeeDto(employee))
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updatedEmployee) {
        Employee employee=employeeRepository.findById(employeeId).orElseThrow(
                ()->new ResourceNotFoudException("Employee is not exits at given id"+employeeId));
        employee.setFisrtName(updatedEmployee.getFirstame());
        employee.setLastName(updatedEmployee.getLastname());
        employee.setEmail(updatedEmployee.getEmail());
      Employee updatedEmployeeoj=  employeeRepository.save(employee);


        return EmployeeMapper.mapToEmployeeDto(updatedEmployeeoj);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee=employeeRepository.findById(employeeId).orElseThrow(
                ()->new ResourceNotFoudException("Employee is not exits at given id"+employeeId));
        employeeRepository.deleteById(employeeId);

    }
}
