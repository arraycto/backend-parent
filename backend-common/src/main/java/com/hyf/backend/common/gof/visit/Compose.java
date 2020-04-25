package com.hyf.backend.common.gof.visit;

/**
 * @Author: Elvis on 2020/4/16
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class Compose implements Visitor {


    @Override
    public void visit(PDFResourceFile resourceFile) {
        String filePath = resourceFile.getFilePath();
        System.out.println("compose : " + filePath + "     " + resourceFile.getClass().getName());
    }

    @Override
    public void visit(HtmlResourceFile resourceFile) {
        String filePath = resourceFile.getFilePath();
        System.out.println("compose : " + filePath + "     " + resourceFile.getClass().getName());
    }
}
