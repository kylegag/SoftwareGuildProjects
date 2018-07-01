/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rocketlaunch.data;

import com.sg.rocketlaunch.models.Launch;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kyle
 */
@Repository
public interface LaunchRepository extends JpaRepository<Launch, Integer> {

    List<Launch> findAll();

    List<Launch> getByLaunchId(int id);
    
    List<Launch> findTop10AllByOrderByDistanceDesc();
    
    @Query( value = "select l.* "
            + "from Launch l "
            + "inner join Rocket r on l.rocket = r.rocketId "
            + "where r.name like ?1% "
            + "and (l.angle = ?2 OR 0.0 = ?2) "
            + "and (l.date = ?3 OR '0001-01-01' = ?3) "
            + "and (l.location = ?4 OR -1 = ?4) "
            + "and (r.body = ?5 OR -1 = ?5) "
            + "and (r.engine = ?6 OR -1 = ?6) "
            + "and (r.fuel = ?7 OR -1 = ?7) "
            + "and (l.distance = ?8 OR 0.0 = ?8)", nativeQuery = true)
    List<Launch> find(String name,
            double angle,
            LocalDate date,
            int locationId,
            int bodyId,
            int engineId,
            int fuelId,
            double distance);
}
