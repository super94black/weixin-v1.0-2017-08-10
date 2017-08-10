//package cn.zhanghl.test;
//
//import cn.zhanghl.dao.WordsDao;
//import cn.zhanghl.pojo.mysql.Words;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * 词汇分割装库
// */
//public class FileTest {
//    public static void main(String args[]){
//        FileTest ft = new FileTest();
//        String str = ft.readFile();
//        System.out.println(str);
//        ft.splitWords(str);
//    }
//    public String readFile(){
//        try {
//            File file = new File("cihui");
//            if(file.isFile() && file.exists()){
//                InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"UTF-8");
//                BufferedReader bufferedReader = new BufferedReader(reader);
//                StringBuilder sb = new StringBuilder();
//                String line = null;
//                while ((line = bufferedReader.readLine()) != null){
//                    sb.append(line);
//                }
//                String str = sb.toString();
//                //去除各种空格 换行 制表符
//                Pattern p = Pattern.compile("\\|\t|\r|\n");
//                Matcher m = p.matcher(str);
//                str = m.replaceAll("");
//                return str;
//            }else{
//                System.out.println("文件不存在");
//                return null;
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            System.out.println("读取出错");
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public String[] splitWords(String words){
//        String [] arr = words.split("\\s+");
//        List list = new ArrayList();
//        String[] newArr = null;
//
//        for (String str : arr) {
//            newArr = str.split("-");
//            list.add(newArr);
//        }
//        Words w = new Words();
//        WordsDao wd = new WordsDao();
//        for(int i = 0; i < list.size(); i++){
//            String[] word = (String[]) list.get(i);
//            w.setWordOne(word[0]);
//            w.setWordTwo(word[1]);
//            w.setCreateTime(System.currentTimeMillis());
//            w.setStatus(1);
//            wd.addWors(w);
//        }
//
//        return null;
//    }
//
//
//}
