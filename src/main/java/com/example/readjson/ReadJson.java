package com.example.readjson;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ReadJson {

    public Employee readJsonMono() {
        Mono<Employee> employeeMono = WebClient
            .create("SERVICE_URL" + "/employees/111")
            .get()
            .retrieve()
            .bodyToMono(Employee.class);

        Employee employee = employeeMono
            .share().block();

        return employee;
    }

    public List<Employee> readJsonFlux() {
        Flux<Employee> employeeFlux = WebClient
            .create("SERVICE_URL" + "/employees")
            .get()
            .retrieve()
            .bodyToFlux(Employee.class);

        List<Employee> employees = employeeFlux
            .collect(Collectors.toList())
            .share().block();

        return employees;
    }

    public String readJsonArrayMono() {

        return null;
    }

}
