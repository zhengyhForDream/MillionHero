package ocr.impl;

import com.baidu.aip.ocr.AipOcr;
import exception.NoRemainingException;
import ocr.OCR;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

/**
 * Created by 618 on 2018/1/12.
 * @author lingfengsan
 */
public class BaiDuOCR implements OCR{
    //设置APPID/AK/SK
    private static String APP_ID = "10697064";
    private static String API_KEY = "Y2Dyel1bZwvsVRS00RZ9iBzh";
    private static String SECRET_KEY = "ED50nYFA3GbhM9AdyoZhC0qqweP9WjtY ";
    private static final AipOcr CLIENT=new AipOcr(APP_ID, API_KEY, SECRET_KEY);
    public static void setAppId(String appId) {
        APP_ID = appId;
    }

    public static void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }

    public static void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }
    BaiDuOCR(){
        // 可选：设置网络连接参数
        CLIENT.setConnectionTimeoutInMillis(2000);
        CLIENT.setSocketTimeoutInMillis(60000);
    }
    @Override
    public String getOCR(File file) {
        Long start=System.currentTimeMillis();
        String path=file.getAbsolutePath();
        // 调用接口
        JSONObject res = CLIENT.basicGeneral(path, new HashMap<String, String>());
        String searchResult=res.toString();
        if(searchResult.contains("error_msg")){
            try {
                throw new NoRemainingException("OCR可使用次数不足");
            } catch (NoRemainingException e) {
                return "OCR可使用次数不足,请使用自己的OCR\n" +
                        "获取方式见:\n" +
                        "https://github.com/lingfengsan/MillionHero/wiki/Android操作步骤\n" +
                        "或者您可选择使用TessOCR\n";
            }
        }
        System.out.println(res.toString());
        JSONArray jsonArray=res.getJSONArray("words_result");
        StringBuilder sb=new StringBuilder();
        for (Object aJsonArray : jsonArray) {
            String str=aJsonArray.toString();
            str=str.substring(10,str.lastIndexOf('"'));
            sb.append(str);
            sb.append("\n");
        }
        Long time=System.currentTimeMillis()-start;
        System.out.println("tessOCR提取信息成功，耗时："+time+"s");
        return sb.toString();
    }

    public static void main(String[] args) {
        OCR ocr=new BaiDuOCR();
        String path = "D:\\Photo\\20180114002647.png";
        String result=ocr.getOCR(new File(path));
        System.out.println(result);
    }
}
