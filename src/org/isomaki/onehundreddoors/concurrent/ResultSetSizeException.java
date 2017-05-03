package org.isomaki.onehundreddoors.concurrent;

/**
 * Created by Chris Isomaki on 5/2/17.
 */
public class ResultSetSizeException extends Exception {
    public ResultSetSizeException(int size) {
        super("Invalid result set size: " + size);
    }
}
