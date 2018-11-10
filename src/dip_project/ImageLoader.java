package dip_project;

import dip_project.util.Util;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Loads images from disk.
 */
public class ImageLoader {
    private boolean optimize = true;
    private GraphicsConfiguration destination = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getDefaultScreenDevice()
            .getDefaultConfiguration();

    /**
     * @param img The optimized image.
     * @return The optimized image.
     */
    public BufferedImage optimize(BufferedImage img) {
        BufferedImage optimized = Util.newOptimizedImageLike(destination, img);
        Graphics2D g2d = optimized.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return optimized;
    }

    public BufferedImage load(File input) throws IOException {
        return isOptimizing() ? optimize(ImageIO.read(input)) : ImageIO
                .read(input);
    }

    public BufferedImage load(URL input) throws IOException {
        return isOptimizing() ? optimize(ImageIO.read(input)) : ImageIO
                .read(input);
    }

    public BufferedImage load(InputStream input) throws IOException {
        return isOptimizing() ? optimize(ImageIO.read(input)) : ImageIO
                .read(input);
    }

    public BufferedImage load(ImageInputStream input) throws IOException {
        return isOptimizing() ? optimize(ImageIO.read(input)) : ImageIO
                .read(input);
    }

    /**
     * Indicate if an images will be optimized after loading or not.
     */
    public boolean isOptimizing() {
        return optimize;
    }

    /**
     * Indicate if an images will be optimized after loading or not.
     *
     * @param optimize True to optimize, false to return the image in it's original format.
     */
    public void setOptimize(boolean optimize) {
        this.optimize = optimize;
    }

    /**
     * Changes the destination graphics configuration. All images will be
     * optimized for this configuration if the isOptimizing() property is set to
     * true.
     *
     * @param destination The destination graphics configuration. If the destination is
     *                    null, the default destination will be restored.
     */
    public void setDestination(GraphicsConfiguration destination) {
        if (destination == null)
            destination = GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice().getDefaultConfiguration();

        this.destination = destination;
    }

    /**
     * Gets destination graphics configuration. All images will be optimized for
     * this configuration if the isOptimizing() property is set to true.
     *
     * @return The destination graphics configuration.
     */
    public GraphicsConfiguration getDestination() {
        return destination;
    }
}
