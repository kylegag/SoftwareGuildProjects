/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.dao;

import com.sg.rainwater.dto.StructureMap;

/**
 *
 * @author Kyle
 */
public interface RainWaterDisplayDao {
        public void write(StructureMap structure) throws RainWaterDaoException;
}
