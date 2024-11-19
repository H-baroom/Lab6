package com.example.lab6.EmployeeController;

import com.example.lab6.ApiResponse.ApiResponse;
import com.example.lab6.EmployeeModel.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    ArrayList<Employee> employees = new ArrayList();

    @GetMapping("/get")
    public ArrayList getEmployees(){
        return employees;
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@RequestBody @Valid Employee employee, Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        for(Employee e:employees){
            if (e.getID().equals(employee.getID())){
                return ResponseEntity.status(400).body(new ApiResponse("Employee exists"));
            }
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("Employee added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateEmployee(@PathVariable String id,@RequestBody @Valid Employee employee,Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getID().equals(id)){
                employees.set(i,employee);
                return ResponseEntity.status(200).body(new ApiResponse("Employee updated"));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("Employee not exists"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteEmployee(@PathVariable String id){
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getID().equals(id)){
                employees.remove(i);
                return ResponseEntity.status(200).body(new ApiResponse("Employee deleted"));
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("Employee not exists"));
    }
    @GetMapping("/searchEmployeesbyPosition/{position}")
    public ArrayList<Employee> searchEmployeesbyPosition(@PathVariable String position){
        ArrayList<Employee> searchPosition = new ArrayList();
        for (Employee e:employees){
            if (e.getPosition().equals(position)){
                searchPosition.add(e);
            }
        }
        return searchPosition;
    }
    @GetMapping("/getEmployeesbyAgeRange/{minAge}/{maxAge}")
    public ArrayList<Employee> getEmployeesbyAgeRange(@PathVariable int minAge, @PathVariable int maxAge){
        ArrayList<Employee> AgeRange = new ArrayList();
        for (Employee e:employees){
            if (e.getAge() >= minAge && e.getAge() <= maxAge){
                AgeRange.add(e);
            }
        }
        return AgeRange;
    }

    @PutMapping("/applyForAnnualLeave/{id}")
    public ResponseEntity applyForAnnualLeave(@PathVariable String id){
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getID().equals(id)){
                if(!(employees.get(i).isOnLeave())){
                    if (employees.get(i).getAnnualLeave() >= 1){
                        employees.get(i).setAnnualLeave(employees.get(i).getAnnualLeave() - 1);
                        employees.get(i).setOnLeave(true);
                        return ResponseEntity.status(200).body(new ApiResponse("Employee Leave successfully"));
                    }
                    //return ResponseEntity.status(400).body(new ApiResponse("Employee can not take a Leave"));
                }
                //return ResponseEntity.status(400).body(new ApiResponse("Employee in Leave"));
            }
            //return ResponseEntity.status(400).body(new ApiResponse("Employee Not exists"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Employee Not exists!!"));
    }

    @GetMapping("/getEmployeesWithNoAnnualLeave")
    public ResponseEntity getEmployeesWithNoAnnualLeave(){
        ArrayList<Employee> employeesWithNoAnnualLeave = new ArrayList();
        for (Employee e:employees){
            if (e.getAnnualLeave() == 0){
                employeesWithNoAnnualLeave.add(e);
            }
        }
        return ResponseEntity.status(200).body(employeesWithNoAnnualLeave);
    }

    @PutMapping("/promoteEmployee/{id}")
    public ResponseEntity  promoteEmployee(@PathVariable String id,@RequestBody @Valid Employee supervisor, Errors errors){
        if (errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getID().equals(id)){
                if(!(employees.get(i).isOnLeave())){
                    if (employees.get(i).getAge() >= 30){
                        if (supervisor.getPosition().equalsIgnoreCase("supervisor") && employees.get(i).getPosition().equalsIgnoreCase("coordinator")){
                            employees.get(i).setPosition("supervisor");
                            return ResponseEntity.status(200).body(new ApiResponse("Employee position supervisor now!"));
                        }
                        //return ResponseEntity.status(400).body(new ApiResponse("requester (user making the request) should be a supervisor"));
                    }
                    //return ResponseEntity.status(400).body(new ApiResponse("employee's age is at least 30 years to promote"));
                }
                //return ResponseEntity.status(400).body(new ApiResponse("Employee in Leave"));
            }
            //return ResponseEntity.status(400).body(new ApiResponse("Employee Not exists"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("Can not promote!!"));
    }

}
