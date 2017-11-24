package com.ntt.acoe.framework.selenium.testdata;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */

public class XMLMethods {

	public static void xmlUpdate(String fileName, String element, String tagName, int pos, String value) {
		try {
			File xmlFile = new File(fileName);
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			Document doc = documentBuilder.parse(xmlFile);
			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			NodeList childNodes = doc.getElementsByTagName(element).item(pos).getChildNodes();
			System.out.println("Nodes:" + childNodes.getLength());
			for (int y = 0; y < childNodes.getLength(); y++) {
				Node data = childNodes.item(y);
				if (data.getNodeName().equals(tagName)) {
					Node child = data.getChildNodes().item(0);
					if (child.getNodeType() == Node.TEXT_NODE) {
						child.setNodeValue(value);
						break;
					}
				}
			}
			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult(fileName);
			transformer.transform(domSource, streamResult);
			System.out.println("File saved to specified path!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
