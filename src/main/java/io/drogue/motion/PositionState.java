package io.drogue.motion;

import java.util.concurrent.atomic.AtomicReference;

import io.drogue.motion.capture.Position;

public class PositionState {

    public PositionState() {

    }

    public void set(Position position) {
        this.position.set(position);
    }

    public Position get() {
        return this.position.get();
    }

    private AtomicReference<Position> position = new AtomicReference<>();
}
