package com.oppo.ux.InstructionLoader;

import org.w3c.dom.Document;
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

public class InstructionLoader {

    private static final String DEFAULT_CONFIG_FILE_PATH =
            "D:\\Code\\plugin\\AdbTools\\config.xml";
    private static final String NODE_NAME_CONFIGS = "instruction-set";
    private static final String NODE_NAME_CONFIG = "instruction";
    private static final String NODE_NAME_NAME = "name";
    private static final String NODE_NAME_DESCRIPTION = "description";
    private static final String NODE_NAME_CODE = "code";

    public static List<InstructionNode> loadConfigXmlFile(File configFile) {
        DocumentBuilderFactory dbf =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            Document document = db.parse(configFile);
            NodeList instructionNodeList = document.getElementsByTagName(NODE_NAME_CONFIG);
//            System.out.println("instruction-set size = "+instructionNodeList.getLength());
            List<InstructionNode> instructions = new ArrayList<>(instructionNodeList.getLength());
            for (int j = 0; j < instructionNodeList.getLength(); j++) {
                NodeList instructionNode = instructionNodeList.item(j).getChildNodes();
//                System.out.println("instruction element size = "+instructionNode.getLength());
                String name = null;
                String description = null;
                String code = null;
                for (int i = 0; i < instructionNode.getLength(); i++) {
                    Node node = instructionNode.item(i);
                    String tagName = node.getNodeName();
                    if (NODE_NAME_NAME.equals(tagName)) {
                        name = getNodeValue(node);
                    } else if (NODE_NAME_DESCRIPTION.equals(tagName)) {
                        description = getNodeValue(node);
                    } else if (NODE_NAME_CODE.equals(tagName)) {
                        code = getNodeValue(node);
                    }
                }
                if (name != null && code != null) {
                    InstructionNode instruction = new InstructionNode(name, description, code);
//                    System.out.println(instruction);
                    instructions.add(instruction);
                }
            }
            return instructions;
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            System.out.println("ConfigLoader loadConfigXmlFile failed: "+e.getMessage());
        }
        return null;
    }

    private static String getNodeValue(Node node) {
        if (node == null) {
            return null;
        }

        String str = null;
        NodeList childNodes = node.getChildNodes();
        Node child = childNodes.item(0);
        if (child != null) {
            str = child.getNodeValue();
        }
        return str;
    }

    private static void isXmlFile(File file) {

    }

}
