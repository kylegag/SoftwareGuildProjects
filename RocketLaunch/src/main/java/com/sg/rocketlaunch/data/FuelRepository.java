/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rocketlaunch.data;

import com.sg.rocketlaunch.models.Fuel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kyle
 */
@Repository
public interface FuelRepository extends JpaRepository<Fuel, Integer> {
    List<Fuel> findAll();
    List<Fuel> getByFuelId(int id);
}
