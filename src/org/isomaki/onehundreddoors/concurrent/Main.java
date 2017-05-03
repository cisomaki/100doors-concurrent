package org.isomaki.onehundreddoors.concurrent;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Chris Isomaki on 5/2/17.
 */
public class Main {
    public static void main(String[] args) {
        final int passes = 100000;
        final int numberOfDoors = passes;

        Result result;
        try {
            result = new Result(numberOfDoors,Main.class);
        } catch (ResultSetSizeException e) {
            System.out.println(e.getLocalizedMessage());
            return;
        }

        // get the number of cores available, we will create one thread per core.
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(cores);
        for (int pass = 0; pass < passes; pass++) {
            Pass passRun = new Pass();
            passRun.setPassNumber(pass);
            passRun.setNumberOfDoors(numberOfDoors);
            passRun.setResultArray(result);
            executorService.execute(passRun);
        }

        System.out.println("Main thread waiting until passes are complete.");
        //wait until we get an interrupt saying results are ready.
        synchronized (Main.class) {
            try {
                Main.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Main thread continuing.");

        try {
            BitSet finalResult = result.getFinalResult();
            int i = 0;
            ArrayList<Integer> openDoors = new ArrayList<>();
            while ((i = finalResult.nextSetBit(i)) >= 0) {
                i++;
                openDoors.add(i);
            }
            System.out.println(openDoors);
        } catch (FinalResultNotReadyException e) {
            System.out.println("Final result did not calculate.");
        }
    }
}
