import ocr.OCR;
import ocr.impl.OCRFactory;
import pattern.Pattern;
import pattern.impl.CommonPattern;
import utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Created by 618 on 2018/1/8.
 *
 * @author lingfengsan
 */
public class Main {
    /**
     * ADB_PATH为自己的adb驱动目录，可以放在resource目录下，也可以自己指定
     * IMAGE_PATH为本机图片存放目录，必须是已存在目录
     */
    private static final String ADB_PATH = "D:\\adb\\adb";
    private static final String IMAGE_PATH = "D:\\Photo";
    private static final OCRFactory OCR_FACTORY = new OCRFactory();
    private static final CommonPattern COMMON_PATTERN = new CommonPattern();
    private static final Utils UTILS = new Utils(ADB_PATH, IMAGE_PATH);

    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        COMMON_PATTERN.setUtils(UTILS);
        //设置OCR方式
        System.out.println("请选择您要使用的文字识别方式\n1.TessOCR\n2.百度OCR");
        System.out.println("默认使用TessOCR，选择后回车,不能为空");
        int selection=Integer.valueOf(bf.readLine());
        COMMON_PATTERN.setOcr(OCR_FACTORY.getOcr(selection));
        //设置搜索方式
        System.out.println("请选择您要使用的搜索方式\n1.百度\n2.搜狗");
        System.out.println("默认使用百度搜索，选择后回车,不能为空");
        selection=Integer.valueOf(bf.readLine());
        COMMON_PATTERN.setSearchSelection(selection);
        //设置游戏模式
        System.out.println("请选择您要进入的游戏\n1.百万英雄\n2.冲顶大会");
        System.out.println("默认为百万英雄，选择后回车");
        selection=Integer.valueOf(bf.readLine());
        COMMON_PATTERN.setPatterSelection(selection);
        System.out.println("设置完成，当题目出现后请按回车键");
        while (true) {
            String str = bf.readLine();
            if ("exit".equals(str)) {
                System.out.println("ヾ(￣▽￣)Bye~Bye~");
                break;
            } else {
                if (str.length() == 0) {
                    System.out.println("开始答题");
                    System.out.println(COMMON_PATTERN.run());
                }
            }
        }


    }
}
