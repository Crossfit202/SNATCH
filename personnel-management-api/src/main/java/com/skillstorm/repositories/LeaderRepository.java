package com.skillstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Leader;

@Repository
public interface LeaderRepository extends CrudRepository<Leader, Integer> {

}
