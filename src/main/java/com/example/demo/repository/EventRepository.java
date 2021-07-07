package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {

  @Query("select e from Event e where e.repoId =:repoid")
  List<Event> findAllByRepoId(@Param("repoid") Integer repoid);

  @Query("select e from Event e where e.actorId =:actorid")
  List<Event> findAllByActorId(@Param("actorid") Integer actorid);
  
  
  @Query("select e from Event e order by id desc limit 1")
  Event findTopByOrderByIdDesc();
}