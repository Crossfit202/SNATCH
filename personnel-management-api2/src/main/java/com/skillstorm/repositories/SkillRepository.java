package com.skillstorm.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.skillstorm.models.Skill;



@Repository
public interface SkillRepository extends CrudRepository<Skill, Integer> {

}
