package com.hyf.backend.common.gof.visit;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Elvis on 2020/4/16
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class Main {
    public static void main(String[] args) {
        ResourceFile resourceFile1 = new PDFResourceFile("/Users/huyufei/IdeaProjects/backend-parent/README.md");

        ResourceFile resourceFile2 = new HtmlResourceFile("/Users/huyufei/IdeaProjects/backend-parent/README.md");


        List<ResourceFile> resourceFiles = Arrays.asList(resourceFile1, resourceFile2);
        Visitor extractor = new Extractor();
        Visitor compose = new Compose();
        for (ResourceFile resourceFile : resourceFiles) {
            resourceFile.accept(extractor);
            resourceFile.accept(compose);
        }



    }
}
