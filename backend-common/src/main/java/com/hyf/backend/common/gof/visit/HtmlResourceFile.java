package com.hyf.backend.common.gof.visit;

/**
 * @Author: Elvis on 2020/4/16
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class HtmlResourceFile extends AbstractResourceFile {


    public HtmlResourceFile(String filePath) {
        super(filePath);
    }


    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
