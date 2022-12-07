package com.zhide.dtsystem.services.instance;

import com.google.common.base.Strings;
import com.zhide.dtsystem.common.fieldObject;
import com.zhide.dtsystem.configs.IgnoreDTDEntityResolver;
import com.zhide.dtsystem.models.xml.ChineseName;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XmlParsor {
    /**
     * @param file Xml文件对象
     * @apiNote 一个节点下的子节点组成目标类的属性值 。
     */
    public static <T> T getSingleByChildNode(File file, String xPath, Class<T> classType) throws Exception {
        T result = classType.newInstance();
        if (file.exists() == false) return result;
        SAXReader reader = new SAXReader();
        reader.setEntityResolver(new IgnoreDTDEntityResolver());
        Document document = reader.read(file);
        Node findNode = document.selectSingleNode(xPath);
        if (findNode != null) {
            Element element = (Element) findNode;
            Field[] fields = classType.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                String fieldName = field.getName();
                ChineseName chineseName = field.getAnnotation(ChineseName.class);
                if (chineseName == null) {
                    List<Node> nodes = element.selectNodes("//" + fieldName);
                    if (nodes.size() > 0) {
                        Element xNode = (Element) nodes.get(0);
                        String value = xNode.getStringValue();
                        fieldObject.setValue(result, field, value);
                    }
                } else {
                    List<Node> yNodes = element.selectNodes(xPath + "/" + chineseName.value());
                    if (field.getType() == java.util.List.class) {
                        List<String> Ps = new ArrayList<>();
                        for (int a = 0; a < yNodes.size(); a++) {
                            Element node = (Element) yNodes.get(a);
                            String secName = chineseName.secName();
                            if (Strings.isNullOrEmpty(secName) == false) {
                                Node ZNode = node.selectSingleNode("//" + chineseName.value() + "/" + secName);
                                if (ZNode != null) {
                                    String X = ZNode.getStringValue();
                                    Ps.add(X);
                                }
                            } else Ps.add(node.getStringValue());
                        }
                        field.setAccessible(true);
                        field.set(result, Ps);
                    } else {
                        if (yNodes.size() > 0) {
                            Element xNode = (Element) yNodes.get(0);
                            String value = xNode.getStringValue();
                            fieldObject.setValue(result, field, value);
                        }
                    }
                }
            }
        }
        return result;
    }

    public static <T> List<T> getByChildNodes(File file, String xPath, Class<T> classType) throws Exception {
        List<T> result = new ArrayList<>();
        if (file.exists() == false) return result;
        SAXReader reader = new SAXReader();
        reader.setEntityResolver(new IgnoreDTDEntityResolver());
        ChineseName chineseClassName = classType.getAnnotation(ChineseName.class);
        Document document = reader.read(file);
        List<Node> findNodes = document.selectNodes(xPath);
        for (int i = 0; i < findNodes.size(); i++) {
            Element node = (Element) findNodes.get(i);
            if (chineseClassName != null) {
                List<String> classNames = Arrays.asList(chineseClassName.value().split(","));
                if (classNames.contains(node.getName()) == false) continue;
            }
            T res = classType.newInstance();
            Field[] fields = classType.getDeclaredFields();
            for (int n = 0; n < fields.length; n++) {
                Field field = fields[n];
                String fieldName = field.getName();
                ChineseName chineseFieldName = field.getAnnotation(ChineseName.class);
                Node tinyNode = null;
                if (chineseFieldName == null) {
                    tinyNode = node.selectSingleNode(xPath + "[" + Integer.toString(i + 1) + "]/" + fieldName);
                } else {
                    String fieldText = chineseFieldName.value();
                    List<String> fieldTexts = Arrays.asList(fieldText.split(","));
                    for (int x = 0; x < fieldTexts.size(); x++) {
                        String X = fieldTexts.get(x);
                        tinyNode = node.selectSingleNode(xPath + "[" + Integer.toString(i + 1) + "]/" + X);
                        if (tinyNode != null) break;
                    }
                }
                if (tinyNode != null) {
                    String value = tinyNode.getStringValue();
                    fieldObject.setValue(res, field, value);
                }
            }
            result.add(res);
        }
        return result;
    }
    public static String getValue(File file,String xPath) throws Exception{
        if (file.exists() == false) return null;
        SAXReader reader = new SAXReader();
        reader.setEntityResolver(new IgnoreDTDEntityResolver());
        Document document = reader.read(file);
        Node findNode = document.selectSingleNode(xPath);
        if(findNode!=null) return findNode.getStringValue(); else return null;
    }
}
