package net.wejudge.utils;

import net.wejudge.controller.LoginController;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;


public class PracticeProperties {

    Properties pps = new Properties();
    OutputStream out = null;

    public PracticeProperties(){
        try {
            // 默认路径
            // 加载配置文件
            pps.load(PracticeProperties.class.getClassLoader().getResourceAsStream("properties/url.properties"));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public PracticeProperties(String path) {
        try {
            // 加载配置文件
            pps.load(new FileInputStream(path));
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * 获取key对应value
     * @param key
     */
    public String getValue(String key) {
        return pps.getProperty(key);
    }

    /**
     * 设置key value
     * @param key
     * @param value
     */
    public void setValue(String key, String value){
        try {
            if (key.trim().equals("") || value.trim().equals("")) {
                return;
            }
            pps.setProperty(key, value);
            // 第二个参数用作写在配置文件的注释，最好对更新内容加以说明
            pps.store(out, "Update " + key);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String [] args) {
        PracticeProperties practiceProperties = new PracticeProperties();
        System.out.println(practiceProperties.getValue("baseUrl"));
    }
}