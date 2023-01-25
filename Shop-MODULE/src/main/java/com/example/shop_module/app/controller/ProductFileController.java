package com.example.shop_module.app.controller;

import com.example.shop_module.app.restClient.RestProductClient;
import io.netty.buffer.ByteBufOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
public class ProductFileController {

    @Autowired
    private final RestProductClient productClient;

    public ProductFileController(RestProductClient productClient) {
        this.productClient = productClient;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public  String provideUploadInfo() {
        return "product_page/fileAdd";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name, @RequestParam("file")MultipartFile file) {
        if (!file.isEmpty()) {
            productClient.addProductFromFile(name, file);
            return "file send";
/*

            try{
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("Shop-MODULE/"+ name + ".txt")));
                stream.write(bytes);
                stream.close();
                return "File Save successful";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "file Not found";
            } catch (IOException e) {
                e.printStackTrace();
                return "file save fail -> " + e.getMessage();
            }

 */
        }
        return "file is Empty";
    }
}
