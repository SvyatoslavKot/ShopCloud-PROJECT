package com.example.productmodule.app.service.documentSevice;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileService {

    public boolean deleteFile(String pathName) {
        try {
            Files.delete(Paths.get(pathName));
            System.out.println("delete -> " + pathName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean saveDocument(String pathName, byte[] documentBytes) {
        if (documentBytes.length > 0) {
            try{
                String pathname = pathName;
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(pathname)));
                stream.write(documentBytes);
                stream.close();
               // log.info( "File Save successful" );
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }else {
            return false;
        }
    }
}
