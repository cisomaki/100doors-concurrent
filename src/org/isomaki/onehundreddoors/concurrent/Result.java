package org.isomaki.onehundreddoors.concurrent;

import java.util.BitSet;

/**
 * Created by Chris Isomaki on 5/2/17.
 */
public class Result {
    private final Object notifyObject;
    private BitSet[] results;
    private BitSet finalResult;

    public Result(int size, Object notifyObject) throws ResultSetSizeException {
        if (size <= 0) {
            throw new ResultSetSizeException(size);
        }
        this.notifyObject = notifyObject;
        this.results = new BitSet[size];
        this.finalResult = new BitSet(size);
    }

    public void setResult(int pass, BitSet result) throws ResultAlreadySetException {
        if (results[pass] != null) {
            throw new ResultAlreadySetException(pass);
        } else {
            results[pass] = result;
            checkResults();
        }
    }

    private void checkResults() {
        boolean foundNull = false;
        for (BitSet result: results) {
            if (result == null) {
                foundNull = true;
                break;
            }
        }
        
        if (!foundNull) {
            calculateFinalResult();
        }
    }

    private void calculateFinalResult() {
        BitSet initialResult = new BitSet(results.length);
        initialResult.set(0,results.length-1,false);
        if (results.length >= 1) {
            for (BitSet result: results) {
                initialResult.xor(result);

            }
            finalResult = initialResult;
            synchronized (notifyObject) {
                notifyObject.notify();
            }
        }
    }

    public BitSet getFinalResult() throws FinalResultNotReadyException {
        if (this.finalResult == null) {
            throw new FinalResultNotReadyException();
        }
        return this.finalResult;
    }
}
