package com.modbus.file;

import com.modbus.pojo.InfoEntity;
import com.modbus.pojo.ReadConfigEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadConfigXML {

    public  NodeList getReadConfigFile(String xmlName,String tagName){
        NodeList _contentList=null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        String projectPath=System.getProperty("user.dir");

//        projectPath=projectPath.substring(0,projectPath.lastIndexOf("\\"));
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            String path=projectPath+File.separator+"xml"+File.separator+xmlName;
            Document document = db.parse("file:///" +path);
            // xml中元素的变量命名均以_符号起始.下同
            Element _message = document.getDocumentElement();
            // 取二级元素content
            _contentList = _message.getElementsByTagName(tagName).item(0).getChildNodes();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return _contentList;
    }

    //获取modbus配置信息
    public static List<ReadConfigEntity> getConfig(){
        List<ReadConfigEntity> list=new ArrayList<>();
        ReadConfigXML readConfigXML=new ReadConfigXML();
        NodeList _contentList=readConfigXML.getReadConfigFile("ReadConfig.xml","modbus");
        if(_contentList!=null){
            for(int i=0;i<_contentList.getLength();i++){
                Node _db1=_contentList.item(i);
                NodeList _dbList=_db1.getChildNodes();
                List<InfoEntity> entities=new ArrayList<>();
                ReadConfigEntity entity=new ReadConfigEntity();
                if(null!=_dbList){
                    for(int j=0;j<_dbList.getLength();j++){
                        Node _db2= _dbList.item(j);
                        if (_db2 instanceof Element) {
                            Element ele = (Element) _db2;
                            InfoEntity infoEntity=new InfoEntity();
                            String _code=ele.getAttribute("code");
                            String _slaveId=ele.getAttribute("slaveId");
                            String _address=ele.getAttribute("address");
                            String _name=ele.getAttribute("name");
                            String _size=ele.getAttribute("size");
                            infoEntity.setSlaveId(Integer.parseInt(_slaveId));
                            infoEntity.setName(_name);
                            infoEntity.setAddress(Integer.parseInt(_address));
                            infoEntity.setCode(_code);
                            infoEntity.setSize(Integer.parseInt(_size));
                            entities.add(infoEntity);
                        }
                    }
                }

                if (_db1 instanceof Element) {
                    Element eles = (Element) _db1;
                    String _ip=eles.getAttribute("ip");
                    String _port=eles.getAttribute("port");
                    String _name=eles.getAttribute("name");
                    String _host=eles.getAttribute("host");
                    entity.setIp(_ip);
                    entity.setPort(Integer.parseInt(_port));
                    entity.setName(_name);
                    entity.setHost(_host);
                    entity.setList(entities);
                    list.add(entity);
                }
            }
        }
        return list;
    }



}
