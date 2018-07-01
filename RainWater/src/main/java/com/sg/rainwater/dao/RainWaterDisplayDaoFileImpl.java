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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kyle
 */
@Service("Not stub")
@Qualifier("Not stub")
public class RainWaterDisplayDaoFileImpl implements RainWaterDisplayDao {
    
    private static final String RAIN_DISPLAY_FILE = "rainDisplay.txt";
    
    @Override
    public void write(StructureMap structure) throws RainWaterDaoException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(RAIN_DISPLAY_FILE));
        } catch (IOException ex) {
            throw new RainWaterDaoException("Structure could not be displayed!", ex);
        }
        
        for(int i = 0; i < structure.getHeight(); i++) {
            for(int j = 0; j < structure.getWidth(); j++) {
                out.print(structure.get(j, i));
            }
            out.flush();
            out.println();
        }
        out.flush();
        out.close();
    }
}
