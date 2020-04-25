package com.hyf.backend.common.gof.visit;

/**
 * @Author: Elvis on 2020/4/16
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public abstract class AbstractResourceFile implements ResourceFile {
    private String filePath;

    public AbstractResourceFile(String filePath) {
        this.filePath = filePath;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
