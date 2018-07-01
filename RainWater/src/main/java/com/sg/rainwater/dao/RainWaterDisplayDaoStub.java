/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.rainwater.dao;

import com.sg.rainwater.dto.StructureMap;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Kyle
 */
public class RainWaterDisplayDaoStub implements RainWaterDisplayDao {

    
    @Override
    public void write(StructureMap structure) throws RainWaterDaoException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter("forTesting_FromDisplayStub"));
        } catch (IOException ex) {
            throw new RainWaterDaoException("Structure could not be displayed!", ex);
        }

        for (int i = 0; i < structure.getHeight(); i++) {
            for (int j = 0; j < structure.getWidth(); j++) {
                out.print(structure.get(j, i));
            }
            out.flush();
            out.println();
        }
        out.flush();
        out.close();
    }

}
