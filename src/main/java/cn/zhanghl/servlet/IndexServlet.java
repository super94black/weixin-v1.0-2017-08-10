package cn.zhanghl.servlet;

import cn.zhanghl.service.CoreService;
import cn.zhanghl.util.Const;
import cn.zhanghl.util.EncryptUtil;
import cn.zhanghl.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Servlet implementation class test
 */
@WebServlet(name="indexServlet",value="/")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String token = Const.Token;

        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        //判断是否四个参数有空
        if (!StringUtil.hasBlank(signature, timestamp, nonce, echostr)) {
            String[] list = {token, timestamp, nonce};
            //按照字典排序
            Arrays.sort(list);

            StringBuilder builder = new StringBuilder();
            //将拼接成一个字符串
            for (String str : list) {
                builder.append(str);
            }
            //进行sha1加密
            String hashcode = EncryptUtil.sha1(builder.toString());
            //和signature比对
            if (hashcode.equalsIgnoreCase(signature)) {

                response.getWriter().println(echostr);

            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        try {
            CoreService.ProcessRequset(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //    //post请求处理
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(
//                            request.getInputStream(), "UTF-8"
//                    )
//            );
//            StringBuilder b = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                b.append(line);
//            }
//            System.out.println(b.toString());
//            reader.close();
//
//            //从request请求中获取输入流
////            InputStream inputStream = request.getInputStream();
//            //通过输入流获取Document对象
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder builder = null;
//            builder = factory.newDocumentBuilder();
////            Document document = builder.parse(inputStream);
//            Document document = builder.parse(
//                    new InputSource(new StringReader(b.toString()))
//            );
//            //<xml>
//            //  <ToUserName><![CDATA[gh_b6a171776f25]]></ToUserName>
//            //  <FromUserName><![CDATA[oiL6j0WJxy7Nagpnt6rX7Yo_5LeM]]></FromUserName><CreateTime>1501741106</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[emmm]]></Content><MsgId>6449928937743195428</MsgId></xml>
//            //获取根节点
//            Element rootNode = document.getDocumentElement();
//            //根节点名
//            String name = rootNode.getNodeName();
//            //获取子节点数组
//            NodeList items = rootNode.getChildNodes();
//            Map<String, String> result = new HashMap<>();
//            //子节点遍历
//            for (int i = 0; i < items.getLength(); i++) {
//                Node item = items.item(i);
//                String iName = item.getNodeName();
//                //<ToUserName><![CDATA[gh_b6a171776f25]]></ToUserName>
//                //注意：ToUserName标签内部的文本内容实际上也是一个节点，这里不能通过getNodeValue直接获取节点内容
//                String value = item.getTextContent();
//                if (iName.equals("#text")) {
//                    continue;
//                }
//                result.put(iName, value);
//            }
//            String Content = result.get("Content");
//            if (Content.equals("1")) {
//                //xml格式化
//                String xml = "<xml>" +
//                        "<ToUserName><![CDATA[%s]]></ToUserName>" +
//                        "<FromUserName><![CDATA[%s]]></FromUserName>" +
//                        "<CreateTime>%s</CreateTime>" +
//                        "<MsgType><![CDATA[%s]]></MsgType>" +
//                        "<Content><![CDATA[%s]]></Content>" +
//                        "</xml>";
//                String toUser = result.get("FromUserName");
//                String fromUser = result.get("ToUserName");
//                String createTime = System.currentTimeMillis() / 1000 + "";
//                String msgType = "text";
//                String content = "hello";
//                //格式化输出
//                String res =String.format(xml, toUser, fromUser, createTime, msgType, content);
//                System.out.println();
//                System.out.println(b.toString());
//                System.out.println(res);
//                //response相应输出
//                response.getWriter().println(res);
//            } else {
//
//            }
//
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        }
//    }

}
