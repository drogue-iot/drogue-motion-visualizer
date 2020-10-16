package io.drogue.motion.capture;

import java.util.Objects;

public class Position {
    public static Position parse(byte[] buf) {
        String str = new String(buf);
        String[] parts = str.split(",");
        if ( parts.length != 4 ) {
            return null;
        }

        return new Position(
                Float.parseFloat(parts[0]),
                Float.parseFloat(parts[1]),
                Float.parseFloat(parts[2]),
                Float.parseFloat(parts[3])
        );
    }

    public Position(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getW() {
        return w;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "Position{" +
                "w=" + w +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Float.compare(position.w, w) == 0 &&
                Float.compare(position.x, x) == 0 &&
                Float.compare(position.y, y) == 0 &&
                Float.compare(position.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(w, x, y, z);
    }

    private float w;
    private float x;
    private float y;
    private float z;
}
