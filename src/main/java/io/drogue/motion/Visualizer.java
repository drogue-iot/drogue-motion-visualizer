package io.drogue.motion;

import io.drogue.motion.capture.DataCaptureServer;
import io.drogue.motion.http.HttpServer;

public class Visualizer {

    public static void main(String...args) throws InterruptedException {
        System.err.println( "RUNNING");
        PositionState state = new PositionState();
        DataCaptureServer capture = new DataCaptureServer(state);
        HttpServer http = new HttpServer(state);
        capture.await();
        http.await();
    }
}
