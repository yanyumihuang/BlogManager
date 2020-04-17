package info.yymh.blogmanager.controller;

import info.yymh.blogmanager.annotation.ControllerLog;
import info.yymh.blogmanager.utils.WordToHtml;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.controller
 * @ClassName:
 * @date 2020/3/29
 * @Description 文章上传接口
 */
@Controller
public class FileAction {
    private final static String fileUploadPath="/src/main/resources/public/articles/";
    @RequestMapping("/uploadImg")
    @ResponseBody
    @ControllerLog("上传图片")
    public HashMap<String, Object> uploadImg(@RequestParam("editormd-image-file") MultipartFile file) throws JSONException {
        HashMap<String ,Object> res = new HashMap<>();
        if (file.isEmpty()){
            res.put("url", "");
            res.put("success", 0);
            res.put("message", "upload fail!");
            return res;
        }
        else {
            String fileName=file.getOriginalFilename();
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(fileUploadPath + fileName);
                Files.write(path, bytes);
                res.put("url", "/articles/"+fileName);
                res.put("success", 1);
                res.put("message", "upload success!");
                return res;
            }
            catch (Exception e){
                e.printStackTrace();
                res.put("url", "");
                res.put("success", 0);
                res.put("message", "upload fail!");
                return res;
            }
        }
    }
    @RequestMapping("/uploadArt")
    @ResponseBody
    @ControllerLog("上传md文档")
    public HashMap<String, Object> uploadArt(@RequestParam("file") MultipartFile file) throws JSONException {
        HashMap<String ,Object> res = new HashMap<>();
        if (file.isEmpty()){
            res.put("url", "");
            res.put("success", 0);
            res.put("message", "upload fail!");
            return res;
        }
        else {
            String fileName=file.getOriginalFilename();
            String prefix=fileName.split("\\.")[0];
            String suffix=fileName.split("\\.")[1];
            try {
                if(suffix.equals("md")){
                byte[] bytes = file.getBytes();
                String content=new String(bytes,"UTF-8");
                WordToHtml wordToHtml=new WordToHtml();
                wordToHtml.parseMd2Html(content,prefix);
                res.put("url", "articles/"+fileName);
                res.put("success", 1);
                res.put("message", "upload success!");
                return res;
                }
                else{
                    res.put("url", "");
                    res.put("success", 0);
                    res.put("message", "upload fail!");
                    return res;
                }
            }
            catch (Exception e){
                e.printStackTrace();
                res.put("url", "");
                res.put("success", 0);
                res.put("message", "upload fail!");
                return res;
            }
        }
    }
    @RequestMapping("/uploadMd")
    @ResponseBody
    @ControllerLog("新增md文档")
    public HashMap<String, Object> uploadMd(String content) throws JSONException {
        HashMap<String ,Object> res = new HashMap<>();
        if (content.isEmpty()){
            res.put("success", 0);
            res.put("message", "upload fail!");
            return res;
        }
        else {
            String fileName=String.valueOf(System.currentTimeMillis());
            try {
                byte[] bytes = content.getBytes();
                File pat=new File("");
                String pa=pat.getCanonicalPath();
                Path path = Paths.get(pa+fileUploadPath + fileName+".html");
                Files.write(path, bytes);
                res.put("url", "articles/"+fileName+".html");
                res.put("title", fileName);
                res.put("success", 1);
                res.put("message", "upload success!");
                return res;
            }
            catch (Exception e){
                e.printStackTrace();
                res.put("url", "");
                res.put("success", 0);
                res.put("message", "upload fail!");
                return res;
            }
        }
    }


}
