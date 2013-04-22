/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CRFH;

import Utils.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Danna
 */
public class LxxDescriptor extends Descriptor {

	/** Constructor. */
	public LxxDescriptor(double scale, int bins){
		super(DescriptorType.DT_Lxx, -128, 128, scale, bins);
	}

    @Override
    public void createRequiredFilters(FilterCache filterCache) {
        GaussianFilterInfo gfi = new GaussianFilterInfo(this.scale);
	CartesianFilterInfo cfi = new CartesianFilterInfo(2, 0);

	filterCache.createFilter(gfi);
	filterCache.createFilter(cfi);
    }

    @Override
    public void createRequiredChannels(ChannelCache channelCache) {
        channelCache.createChannel(ChannelType.CT_L);
    }
    
    @Override
    public void createRequiredScales(ScaleSpaceCache scaleSpaceCache){
     scaleSpaceCache.createScaleSpaceSample(ChannelType.CT_L, this.scale);
    }

    @Override
    public int[] apply(BufferedImage image, ChannelCache channelCache, ScaleSpaceCache scaleSpaceCache, FilterCache filterCache) {

        BufferedImage result = null;

        result = scaleSpaceCache.getScaleSpaceSample(ChannelType.CT_L, this.scale);
        
//        GaussianFilterInfo gfi = new GaussianFilterInfo(this.scale);
//        result = filterCache.applyFilter(gfi, image);

        // Filter with second-derivative filter
        CartesianFilterInfo cfi = new CartesianFilterInfo(2, 0);
        result = filterCache.applyFilter(cfi, result);
        
    //    Image.saveAsJpg(result, "LxxDescBefore");

//        int width = result.getWidth();
//        int height = result.getHeight();
        
        int[] input = Image.getPixels(result);
        
        // Normalize
        double factor = this.scale; //  Math.sqrt(this.scale); //
        for (int i = 0; i < input.length; i++) {
            input[i] *= factor;
        }
        
//         Image.createImageFromIntArray(input, width, height, "LxxDesc");

        // Return the result
        return input;

    }

};
