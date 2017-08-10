//package cn.zhanghl.test;
//
//import org.dom4j.*;
//import org.xml.sax.SAXException;
//
//import javax.xml.parsers.ParserConfigurationException;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class XMLTest {
//    public  static String name = "";
//    public  static String score = "";
//    public  static String type = "";
//    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, DocumentException {
//        XMLTest x = new XMLTest();
//        x.XmlToMap();
//    }
//    public void XmlToMap() throws ParserConfigurationException, IOException, SAXException, DocumentException {
//        String xmlStr = this.fileToString();
//        Document document = DocumentHelper.parseText(xmlStr);
//        //获得根节点
//        Element rootElement = document.getRootElement();
//        //调用转化Map方法
//        List nodeMap = new ArrayList();
//        nodeMap = this.Map(rootElement,nodeMap);
////        System.out.println("1");
////        this.printf(nodeMap);
//
//    }
//
//    /**
//     * 递归解析XML
//     * @param nodes
//     * @param nodeMap
//     * @return
//     */
//    public List Map(Element nodes, List nodeMap){
//        //遍历该结点下的子节点
//        for (Iterator iter = nodes.elementIterator(); iter.hasNext();) {
//            Element element = (Element) iter.next();
//            if (element.isTextOnly()) {
//                String key = element.getName();
//                String value = element.getText();
//                Map<String,String> map = new HashMap<>();
//                map.put(key,value);
//                nodeMap.add(map);
//                if(key.equals("课程")){
//                    name = value;
//                }if(key.equals("成绩")){
//                    score = value;
//                }
//                if(key.equals("课类")){
//                    type = value;
//                }
//            }else{
//                System.out.println("");
//                List list = new ArrayList<>();
//                //如果该节点有子节点 递归调用
//                String k = element.getName();
//                String v = element.getText();
//                Map<String,String> m = new HashMap<>();
//                m.put(k,v);
//                list.add(m);
//                List allList = this.Map(element,list);
//                nodeMap.add(allList);
//            }
//        }
//        return nodeMap;
//    }
//
//    /**
//     * 文件转换成字符串
//     * @return
//     * @throws IOException
//     */
//    public String fileToString() throws IOException {
//        BufferedReader bufferedReader =new BufferedReader(new FileReader("score.xml"));
//        StringBuilder stringBuilder=new StringBuilder();
//        String content;
//        while((content=bufferedReader.readLine())!=null){
//            stringBuilder.append(content);
//        }
//        String strXml = stringBuilder.toString();
//        //去除各种空格 换行 制表符
//        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
//        Matcher m = p.matcher(strXml);
//        strXml = m.replaceAll("");
//        return strXml;
//    }
//    public void printf(List list){
//
//    }
//}
