package com.skillstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Personnel;

@Repository
public interface PersonnelRepository extends CrudRepository<Personnel, Integer>{

}
