package top.misec.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import lombok.extern.slf4j.Slf4j;

/**
 * 工具类通过流的方式读取文件.
 *
 * @author Junzhou Liu
 * @since 2020/10/17 19:31
 */
@Slf4j
public class ReadFileUtils {

    /**
     * 读取指定路径的文件。
     *
     * @return fileContentStr
     */
    public static String readFile(String filePath) {
        String fileContentStr = null;
        try {
            InputStream inputStream = new FileInputStream(filePath);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            fileContentStr = new String(buffer, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            log.debug("file not found", e);
        } catch (IOException e) {
            log.warn("", e);
        }
        return fileContentStr;
    }


    /**
     * 从resource读取版本文件.
     *
     * @param fileName 文件名.
     * @return fileContentStr
     */
    public static String loadJsonFromAsset(String fileName) {
        String fileContentStr = null;
        try {
            InputStream is = ReadFileUtils.class.getClassLoader().getResourceAsStream(fileName);
            assert is != null;
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            fileContentStr = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.warn("", e);
        }
        return fileContentStr;
    }

}
