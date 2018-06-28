/*===========================================================================
COPYRIGHT 2013 Vinícius G. Mendonça ALL RIGHTS RESERVED.

This software cannot be copied, stored, distributed without
Vinícius G. Mendonça prior authorization.

This file was made available on https://github.com/ViniGodoy and it
is free to be redistributed or used under Creative Commons license 2.5 br:
http://creativecommons.org/licenses/by-sa/2.5/br/
============================================================================*/
package dip_project.transform;

import dip_project.color.ColorChannel;
import dip_project.filters.PixelByPixelFilter;

import java.awt.image.BufferedImage;

public class Negation extends PixelByPixelFilter {
    public Negation(ColorChannel... channels) {
        super(channels);
    }

    @Override
    protected int processChannel(BufferedImage img, ColorChannel channel, int x,
                                 int y, int tone) {
        return channel.tones() - tone - 1;
    }
}
