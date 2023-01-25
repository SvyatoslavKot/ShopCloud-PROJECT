package com.example.productmodule.app.service.documentSevice;

import com.example.productmodule.app.dto.ProductDTO;
import com.example.productmodule.app.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class DocumentServiceTXT implements DocumentService{
    @Autowired
    private ProductService productService;
    private StringParser parser = new StringParser();
    private FileService fileService = new FileService();
    private ExecutorService executorService = Executors.newWorkStealingPool(4);

    private Queue<String> stringQueue = new ArrayDeque<>();
    private Queue<ProductDTO> productDTOQueue = new ArrayDeque<>();

    volatile boolean readEnd = false;
    volatile boolean saveEnd = false;
    volatile boolean parceEnd = false;

    @Override
    public void SaveFromDocument (String documentName, byte[] documentBytes) {
        String pathname = "Product-MODULE/"+ documentName + ".txt";
        if (fileService.saveDocument(pathname, documentBytes)){

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    readDocument(pathname);
                    synchronized (fileService) {
                        fileService.notifyAll();
                    }
                }
            });

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    parseDocument();
                    synchronized (fileService) {
                        fileService.notifyAll();
                    }
                }
            });

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    saveToDB();
                    synchronized (fileService) {
                        fileService.notifyAll();
                    }
                }
            });
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    deleteFile(pathname);
                }
            });
        }

    }
    private synchronized void readDocument (String pathName) {
                BufferedReader reader = null;
                try {
                    readEnd = false;
                    reader = new BufferedReader(new FileReader(new File(pathName)));
                    String line = reader.readLine();
                    while (line != null) {
                        stringQueue.add(line);
                        System.out.println("add");
                        synchronized (this){
                            this.wait(500);
                        }
                        synchronized (parser) {
                            parser.notifyAll();
                        }
                        line = reader.readLine();
                    }
                    reader.close();

                    readEnd = true;
                } catch(FileNotFoundException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    }

    private void deleteFile(String pathName) {
        try{
            while (!readEnd && !saveEnd && !parceEnd) {
                synchronized (fileService) {
                    fileService.wait();
                }
            }
            fileService.deleteFile(pathName);

        }catch (InterruptedException e) {
            e.getLocalizedMessage();
        }
    }

    private void parseDocument() {
        parceEnd = false;
        try{
            while (!parceEnd) {
                while (stringQueue.isEmpty() && !readEnd) {
                    synchronized (parser) {
                        parser.wait();
                    }
                }if (readEnd && stringQueue.isEmpty()) {
                    parceEnd = true;
                }else {
                    ProductDTO dto = parser.parseStringToProductDto(stringQueue.poll());
                    if (dto != null) {
                        productDTOQueue.add(dto);
                    }
                    synchronized (productService){
                        productService.notifyAll();
                    }

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private void saveToDB() {
                saveEnd = false;
        System.out.println(saveEnd + " " + stringQueue.size() + " " + readEnd);
                try{
                    while (!saveEnd) {
                        while (productDTOQueue.isEmpty() && !parceEnd){
                            synchronized (productService){
                                productService.wait();
                                System.out.println("wait");
                            }
                        }
                        if (parceEnd && productDTOQueue.isEmpty()) {
                            saveEnd = true;
                            System.out.println("end");
                        }else {
                            productService.addProduct(productDTOQueue.poll());
                            System.out.println("save");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
    }

}
