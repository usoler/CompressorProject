package domain.dataObjects;

public class Pixel {

    private float x;
    private float y;
    private float z;

    /**
     * Constructs a new {@link Pixel}
     *
     * @param x the first pixel value
     * @param y the second pixel value
     * @param z the third pixel value
     */
    public Pixel(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructs a copy of a given {@link Pixel}
     *
     * @param pixel a new {@link Pixel} with the same values
     */
    public Pixel(Pixel pixel) {
        this.x = pixel.getX();
        this.y = pixel.getY();
        this.z = pixel.getZ();
    }

    /**
     * Gets the first pixel value
     *
     * @return the first pixel value
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the second pixel value
     *
     * @return the second pixel value
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the third pixel value
     *
     * @return the third pixel value
     */
    public float getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pixel pixel = (Pixel) o;
        return x == pixel.x &&
                y == pixel.y &&
                z == pixel.z;
    }
}
