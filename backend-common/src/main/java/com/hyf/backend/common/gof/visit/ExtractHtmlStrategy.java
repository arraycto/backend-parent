package com.hyf.backend.common.gof.visit;

/**
 * @Author: Elvis on 2020/4/16
 * @Email: yfelvis@gmail.com
 * @Desc: TODO
 */
public class ExtractHtmlStrategy implements ExtractTextStrategy {
    private String filePath;
    @Override
    public void extractText() {
        System.out.println("提取html的文字...");
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
