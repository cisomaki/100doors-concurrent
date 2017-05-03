package org.isomaki.onehundreddoors.concurrent;

import org.junit.Test;

import java.util.BitSet;

import static org.junit.Assert.*;

/**
 * Created by Chris Isomaki on 5/3/17.
 */
public class ResultSetTest {
    @Test
    public void addResultTwice() {
        try {
            Result resultSet = new Result(2,Thread.currentThread());
            BitSet resultA = new BitSet(2);
            BitSet resultB = new BitSet(2);
            resultSet.setResult(0, resultA);
            resultSet.setResult(0, resultB);
        } catch (ResultAlreadySetException e) {
            assertTrue(true);
        } catch (ResultSetSizeException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    public void calculateResult() {
        try {
            Result resultSet = new Result(2,Thread.currentThread());
            BitSet resultA = new BitSet(2);
            BitSet resultB = new BitSet(2);
            resultA.set(0,true);
            resultA.set(1,false);
            resultB.set(0,false);
            resultB.set(1,true);
            resultSet.setResult(0, resultA);
            resultSet.setResult(1, resultB);
            BitSet finalResult = resultSet.getFinalResult();
        } catch (ResultAlreadySetException e) {
            e.printStackTrace();
            assertTrue(false);
        } catch (ResultSetSizeException | FinalResultNotReadyException e) {
            e.printStackTrace();
            assertTrue(false);
        }
    }
}