package org.isomaki.onehundreddoors.concurrent;

import java.util.BitSet;

/**
 * Created by Chris Isomaki on 5/2/17.
 */
public class Pass implements Runnable {
    private int passNumber;
    private int numberOfDoors;

    private BitSet passResult;
    private Result result;

    @Override
    public void run() {
        int i = passNumber;
//        System.out.println("Running pass number: " + passNumber);
        BitSet doors = new BitSet();
        while (i < numberOfDoors) {
            doors.set(i, true);
            i += (passNumber+1);
        }
        try {
            result.setResult(passNumber,doors);
        } catch (ResultAlreadySetException e) {

        }
    }

    public void setPassNumber(int passNumber) {
        this.passNumber = passNumber;
    }

    public void setNumberOfDoors(int numberOfDoors) {
        this.numberOfDoors = numberOfDoors;
    }

    public void setResultArray(Result result) {
        this.result = result;
    }
}
