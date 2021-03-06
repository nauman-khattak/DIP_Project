package dip_project.transform;

import dip_project.processing.AbstractProcessing;
import dip_project.util.Util;

import java.awt.image.BufferedImage;

public class CropToBorder extends AbstractProcessing {
    @Override
    public BufferedImage process(BufferedImage source) {
        int total = 2 * source.getWidth() + 2 * (source.getHeight() - 2);
        int count = 0;
        fireProcessingStarted(total, "bordas");
        BufferedImage dest = Util.newOptimizedImageLike(source);
        for (int x = 0; x < source.getWidth(); ++x) {
            int h = source.getHeight() - 1;
            dest.setRGB(x, 0, source.getRGB(x, 0));
            dest.setRGB(x, h, source.getRGB(x, h));

            count += 2;
            fireProcessingProgress(count, total);
        }

        for (int y = 1; y < source.getHeight() - 2; ++y) {
            int w = source.getWidth() - 1;
            dest.setRGB(0, y, source.getRGB(0, y));
            dest.setRGB(w, y, source.getRGB(w, y));

            count += 2;
            fireProcessingProgress(count, total);
        }
        fireProcessingEnded(total, dest);
        return dest;
    }

}
