package com.MicroServicios.companies_crud.repositories;


import com.MicroServicios.companies_crud.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository  extends JpaRepository<Company,Long> {

    Optional<Company>findByName (String name);


}