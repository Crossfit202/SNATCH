package com.skillstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Captain;

@Repository
public interface CaptainRepository extends CrudRepository<Captain, Integer> {

}
