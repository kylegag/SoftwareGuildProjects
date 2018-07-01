/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.powerball.service;

import com.sg.powerball.data.PowerballDao;
import com.sg.powerball.models.Pick;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Kyle
 */
@Component
public class PowerballService {

    @Autowired
    private PowerballDao dao;

    // Seperate into two methods and throw one exception each
    public void checkIfPickIsValid(Pick pick) throws PickOutOfRangeException, PickNumbersNotUniqueException {
        
        List<Integer> firstFiveNumbers = new ArrayList<>();
        Set<Integer> pastNumbers = new HashSet<>();

        firstFiveNumbers.add(pick.getFirstNum());
        firstFiveNumbers.add(pick.getSecondNum());
        firstFiveNumbers.add(pick.getThirdNum());
        firstFiveNumbers.add(pick.getFourthNum());
        firstFiveNumbers.add(pick.getFifthNum());
        
        for(Integer num : firstFiveNumbers) {
            if(!(1 <= num && num <= 69)) {
                throw new PickOutOfRangeException();
            }
        }
        if(!(1 <= pick.getPowerBall() && pick.getPowerBall() <= 69)) {
            throw new PickOutOfRangeException();
        }
        
        Iterator<Integer> iter = firstFiveNumbers.iterator();
        int nextNumber;
        while(iter.hasNext()) {
            nextNumber = iter.next();
            if(!pastNumbers.contains(nextNumber)) {
                pastNumbers.add(nextNumber);
            } else {
                throw new PickNumbersNotUniqueException();
            }
        }

    }

    public List<Pick> search(Pick pick) {
        return dao.search(pick.getFirstName(), pick.getLastName(), pick.getEmail(), pick.getState(), pick.getPickId());
    }

    public List<Pick> search(String firstName, String lastName, String email,
            String state, int pickId) {
        return dao.search(firstName, lastName, email, state, pickId);
    }

    public boolean addPick(Pick p) {
        return dao.addPick(p);
    }

    public void markAllAsInvalid() {
        dao.markAllAsInvalid();
    }

    public Pick findClosestMatch(Pick pick) {
        int currScore;
        int maxScore = 0;
        Pick nextPick;
        Pick bestMatch = null;
        List<Pick> picks = dao.getAll();
        Iterator<Pick> iter = picks.iterator();
        while (iter.hasNext()) {
            nextPick = iter.next();
            currScore = 0;
            if (pick.getFirstNum() == nextPick.getFirstNum()) {
                currScore += 2;
            } else if (pick.getSecondNum() == nextPick.getSecondNum()) {
                currScore += 2;
            } else if (pick.getThirdNum() == nextPick.getThirdNum()) {
                currScore += 2;
            } else if (pick.getFourthNum() == nextPick.getFourthNum()) {
                currScore += 2;
            } else if (pick.getFifthNum() == nextPick.getFifthNum()) {
                currScore += 2;
            } else if (pick.getPowerBall() == nextPick.getPowerBall()) {
                currScore += 3;
            }

            if (maxScore < currScore) {
                bestMatch = nextPick;
                maxScore = currScore;
            }
        }
        return bestMatch;
    }

    public Pick generateQuickPickOrDrawing() {
        Pick pick = new Pick();
        List<Integer> quickPickList = new ArrayList<>();
        List<Integer> oneThroughSixtyNine = new ArrayList<>();
        Random rd = new Random();
        int firstNum;
        int secondNum;
        int thirdNum;
        int fourthNum;
        int fifthNum;
        int powerBall;

        for (int i = 1; i < 70; i++) {
            oneThroughSixtyNine.add(i);
        }

        quickPickList.add(firstNum = oneThroughSixtyNine.get(rd.nextInt(69)));
        oneThroughSixtyNine.remove(new Integer(firstNum));
        quickPickList.add(secondNum = oneThroughSixtyNine.get(rd.nextInt(68)));
        oneThroughSixtyNine.remove(new Integer(secondNum));
        quickPickList.add(thirdNum = oneThroughSixtyNine.get(rd.nextInt(67)));
        oneThroughSixtyNine.remove(new Integer(thirdNum));
        quickPickList.add(fourthNum = oneThroughSixtyNine.get(rd.nextInt(66)));
        oneThroughSixtyNine.remove(new Integer(fourthNum));
        quickPickList.add(fifthNum = oneThroughSixtyNine.get(rd.nextInt(65)));
        quickPickList.add(powerBall = rd.nextInt(26) + 1);

        pick.setFirstNum(quickPickList.get(0));
        pick.setSecondNum(quickPickList.get(1));
        pick.setThirdNum(quickPickList.get(2));
        pick.setFourthNum(quickPickList.get(3));
        pick.setFifthNum(quickPickList.get(4));
        pick.setPowerBall(quickPickList.get(5));

        return pick;
    }
}
