package com.hyf.backend.common.gof.visit;

import java.io.File;

/**
 * @Author: Elvis on 2020/4/16
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public interface ResourceFile {
    void accept(Visitor extractor);
    String getFilePath();
}
