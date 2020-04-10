package info.yymh.blogmanager.utils;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.HtmlRenderer;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.utils
 * @ClassName:
 * @date 2020/4/6
 * @Description word转html
 */
public class WordToHtml {
    public void parseDocx2Html() throws Throwable {
        final String path = "F:\\";
        final String file = "xxxxxxx.doc";
        InputStream input = new FileInputStream(path + file);
        String suffix = file.substring(file.indexOf(".")+1);// //截取文件格式名

        //实例化WordToHtmlConverter，为图片等资源文件做准备
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .newDocument());
        wordToHtmlConverter.setPicturesManager(new PicturesManager() {
            @Override
            public String savePicture(byte[] content, PictureType pictureType,
                                      String suggestedName, float widthInches, float heightInches) {
                return suggestedName;
            }
        });
        if ("doc".equals(suffix.toLowerCase())) {
            // docx
            HWPFDocument wordDocument = new HWPFDocument(input);
            wordToHtmlConverter.processDocument(wordDocument);
            //处理图片，会在同目录下生成 image/media/ 路径并保存图片
            List pics = wordDocument.getPicturesTable().getAllPictures();
            if (pics != null) {
                for (int i = 0; i < pics.size(); i++) {
                    Picture pic = (Picture) pics.get(i);
                    try {
                        pic.writeImageContent(new FileOutputStream(path
                                + pic.suggestFullFileName()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // 转换
        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(outStream);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        outStream.close();
        String content = new String(outStream.toByteArray());
        FileUtils.writeStringToFile(new File(path, "interface.html"), content,
                "utf-8");
    }
    public  void  parseMd2Html(String content,String prefix){
   /*     String aa="";
        FileInputStream fileInputStream=null;
        try {
            RandomAccessFile rf = new RandomAccessFile(filePath, "rw");
            FileChannel fileChannel=rf.getChannel();
            CharsetDecoder decoder = Charset.forName("utf-8").newDecoder();
            int capacity = 2400;
            ByteBuffer byteBuffer=ByteBuffer.allocate(capacity);
            CharBuffer charBuffer = CharBuffer.allocate(capacity);
            int bytesRead = fileChannel.read(byteBuffer);
            //存储为转换的字符
            char[] tmp = null;
            byte[] remainByte = null;
            int leftNum = 0;
            while ( bytesRead!=-1){
                byteBuffer.flip();
                //将可以解析的字符从byte变为char，此时游标位置也发生变化
                decoder.decode(byteBuffer, charBuffer, true);
                charBuffer.flip();
                remainByte = null;
                //如果刚好全部都能变为char，则不会乱码，limit是byte buffer的容量，position则是从byte变为char操作后的位置
                leftNum=byteBuffer.limit()-byteBuffer.position();
                if (leftNum > 0) {
                    remainByte = new byte[leftNum];
                    //将未转换的放入byte数组
                    byteBuffer.get(remainByte, 0, leftNum);
                }
                tmp = new char[charBuffer.length()];
                while (charBuffer.hasRemaining()) {
                    charBuffer.get(tmp);
                    aa+=(new String(tmp));
                }
                //aa+= Charset.forName("UTF-8").decode(byteBuffer).toString();
                byteBuffer.clear();
                charBuffer.clear();
                //将未完成的byte放入byteBuffer进行下次解析，同时改变bytebuffer位置
                if (remainByte != null) {
                    byteBuffer.put(remainByte);
                }
                 bytesRead= fileChannel.read(byteBuffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (fileInputStream!=null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
        List<Extension> extensions = Arrays.asList(TablesExtension.create(), AutolinkExtension.create(), StrikethroughExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(content);
        HtmlRenderer renderer =
                HtmlRenderer.builder().extensions(extensions).attributeProviderFactory(context -> new HtmlAttributeProvider()).build();
        String html=renderer.render(document);
        String fileName="D:\\IDEA\\work\\BlogManager\\src\\main\\resources\\public\\articles"+prefix+".html";
        try {
            BufferedWriter  writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(html);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    class HtmlAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
            if (node instanceof TableBlock) {
                attributes.put("class", "layui-table");
            }
        }
    }
}
