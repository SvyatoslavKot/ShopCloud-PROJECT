package com.example.shop_module.app.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.xmlbeans.StringEnumAbstractBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ReadDocxFile {

    public String read() {
        String r = new String();
        try {
            FileInputStream fis = new FileInputStream("shool_example.docx");
            XWPFDocument docxFile = new XWPFDocument(OPCPackage.open(fis));
            List<XWPFParagraph> paragraphs = docxFile.getParagraphs();
            XWPFTable table = docxFile.getTables().get(0);
            r = table.getRow(1).getCell(1).getText();
            System.out.println("r =>" + r );
            String s = "";
            for (XWPFParagraph p : paragraphs) {
                System.out.println(p.getText());
                s = s + p;
            }
            return r;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return r;
    }
}
