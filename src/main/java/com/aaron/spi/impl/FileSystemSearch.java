package com.aaron.spi.impl;

import com.aaron.spi.FileSearch;

/**
 * @author FengHaixin
 * @description 一句话描述该文件的用途
 * @date 2018/1/5
 */
public class FileSystemSearch implements FileSearch {

    @Override
    public void searchFile() {

        System.out.println("FileSystemSearch");
    }
}
