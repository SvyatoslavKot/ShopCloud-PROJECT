package com.example.shop_module.app.util;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class SaveImage {

    public void saveImage(String name, byte[] bytes, String imageFormat) throws IOException {
        String path = "Shop-MODULE/src/main/resources/static/img/"+ name + "." + imageFormat;

        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(new File(path)));
        stream.write(bytes);
        stream.close();
    }

    public void saveAndCompress(String name, byte[] bytes, String imageFormat) throws IOException {
        saveImage(name,bytes,imageFormat);
        String path = "Shop-MODULE/src/main/resources/static/img/"+ name + "." + imageFormat;

        File input = new File(path);

        BufferedImage image = ImageIO.read(input);

        File compressedImageFile = new File("Shop-MODULE/compress_"+ name + ".jpeg");
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers =  ImageIO.getImageWritersByFormatName("jpeg");
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.70f);
        writer.write(null, new IIOImage(image, null, null), param);

        os.close();
        ios.close();
        writer.dispose();
    }
}
