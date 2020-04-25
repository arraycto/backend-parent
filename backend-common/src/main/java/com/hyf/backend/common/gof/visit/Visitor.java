package com.hyf.backend.common.gof.visit;

/**
 * @Author: Elvis on 2020/4/16
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface Visitor {
    void visit(PDFResourceFile resourceFile);

    void visit(HtmlResourceFile resourceFile);
}
