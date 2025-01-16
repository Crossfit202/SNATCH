package com.skillstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Loot;

@Repository
public interface LootRepository extends CrudRepository<Loot, Integer> {

}
