package com.hyf.backend.common.gof.visit;

/**
 * @Author: Elvis on 2020/4/16
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class Extractor implements Visitor{


    @Override
    public void visit(PDFResourceFile resourceFile) {
        String filePath = resourceFile.getFilePath();
        System.out.println("extract text: " + filePath + "     " + resourceFile.getClass().getName());
    }

    @Override
    public void visit(HtmlResourceFile resourceFile) {
        String filePath = resourceFile.getFilePath();
        System.out.println("extract text: " + filePath + "     " + resourceFile.getClass().getName());
    }
}
