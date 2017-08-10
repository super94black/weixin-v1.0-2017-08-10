package cn.zhanghl.util;


import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import cn.zhanghl.util.CurlUtil;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;


/**
 * xml中区分 Node Element Attribute三者区别，注意Node的种类
 */
public class XMLUtil {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        String xml = cn.zhanghl.util.CurlUtil.getContent("http://www.w3school.com.cn/example/xmle/plant_catalog.xml", null, "GET");
        StringReader reader = new StringReader(xml);
        InputSource source = new InputSource(reader);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(source);
        //获取根节点元素
        StringBuilder stringBuilder = new StringBuilder();

        Element rootNode = document.getDocumentElement();
        //获取根节点下的子节点
        NodeList nodeList = rootNode.getChildNodes();
//        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
//        for(int i = 0; i < nodeList.getLength(); i++){
//            NodeList nodes = nodeList.item(i).getChildNodes();
//            Map<String,String> maps = new HashMap<String,String>();
//            for(int j = 0; j < nodes.getLength(); j++){
//                String key = nodes.item(j).getNodeName();
//                String value = nodes.item(j).getNodeValue();
//                maps.put(key,value);
//            }
//            list.add(maps);
//        }
//        System.out.println(list.toString());
        readNode(rootNode, stringBuilder);
        String out = stringBuilder.toString();
        System.out.println(out);
    }

    /**
     * XML转化成Map
     * @param xml
     * @return
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    public static Map<String,String> getXMLToMap(String xml) throws ParserConfigurationException, IOException, SAXException {
        //通过输入流获取Document对象

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        builder = factory.newDocumentBuilder();
        StringReader reader = new StringReader(xml);
        InputSource in = new InputSource(reader);
        Document document = builder.parse(in);
        //<xml>
        //  <ToUserName><![CDATA[gh_b6a171776f25]]></ToUserName>
        //  <FromUserName><![CDATA[oiL6j0WJxy7Nagpnt6rX7Yo_5LeM]]></FromUserName><CreateTime>1501741106</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[emmm]]></Content><MsgId>6449928937743195428</MsgId></xml>
        //获取根节点
        Element rootNode = document.getDocumentElement();
        //根节点名
        String name = rootNode.getNodeName();
        //获取子节点数组
        NodeList items = rootNode.getChildNodes();
        Map<String, String> result = new HashMap<>();
        //子节点遍历
        for (int i = 0; i < items.getLength(); i++) {
            Node item = items.item(i);
            String iName = item.getNodeName();
            //<ToUserName><![CDATA[gh_b6a171776f25]]></ToUserName>
            //注意：ToUserName标签内部的文本内容实际上也是一个节点，这里不能通过getNodeValue直接获取节点内容
            String value = item.getTextContent();
            if (iName.equals("#text")) {
                continue;
            }
            result.put(iName, value);
        }
        reader.close();
        return result;
    }
    /**
     * 递归解析xml
     * @param node
     * @param builder
     */
    public static void readNode(Node node, StringBuilder builder) {
        if (node.getNodeType() == Node.TEXT_NODE) {
            if (!node.getTextContent().trim().equals("")) {
                builder.append(node.getNodeValue());
            }
        } else {
            String nName = node.getNodeName();
            builder.append("<").append(nName);
            if (node.hasAttributes()) {
                NamedNodeMap attrs = node.getAttributes();
                for (int i = 0; i < attrs.getLength(); i++) {
                    Node attr = attrs.item(i);
                    String aName = attr.getNodeName();
                    String value = attr.getNodeValue();
                    builder.append(" ").append(aName).append("=").append(value);
                }
            }
            builder.append(">");
            if (node.hasChildNodes()) {
                NodeList childNodes = node.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node childNode = childNodes.item(i);
                    readNode(childNode, builder);
                }
            }
            builder.append("</").append(nName).append(">");
        }
    }
}