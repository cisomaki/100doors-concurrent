package org.isomaki.onehundreddoors.concurrent;

/**
 * Created by Chris Isomaki on 5/2/17.
 */
public class ResultAlreadySetException extends Exception {
    public ResultAlreadySetException(int pass) {
        super("Result already set for pass: " + pass);
    }
}
