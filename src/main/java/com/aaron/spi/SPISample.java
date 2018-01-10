package com.aaron.spi;

import java.util.ServiceLoader;

/**
 * java spi机制，定义接口，实现由不同公司自定义实现方式
 *
 * @author FengHaixin
 * @description 一句话描述该文件的用途
 * @date 2018/1/5
 */
public class SPISample {

    public static void main(String[] args) {

        ServiceLoader<FileSearch> serviceLoader = ServiceLoader.load(FileSearch.class);

        for (FileSearch aServiceLoader : serviceLoader) {

            aServiceLoader.searchFile();
        }
    }
}
