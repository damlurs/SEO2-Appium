package com.ntt.acoe.framework.selenium.testdata;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * @author Santosh Hariprasad (NTT Badge Id: 244583,
 *         Santhosh.Hariprasad@NTTDATA.com)
 * @version 1.0
 * @since 2015-01-01
 */

public class XMLUpdate {

	public void xmlRead(String tagName, String value) {
		try {

			File xmlFile = new File("C:/PER999/DataPool/PREMIUM_BILLING_2_5_001.xml");
			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			Document doc = documentBuilder.parse(xmlFile);
			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			// NodeList nodes = doc.getElementsByTagName("funds");

			NodeList childNodes = doc.getElementsByTagName("funds").item(0).getChildNodes();
			System.out.println("Nodes:" + childNodes.getLength());
			for (int y = 0; y < childNodes.getLength(); y++) {
				System.out.println("In loop");
				Node data = childNodes.item(y);
				System.out.println("Data" + data.getNodeName());
				System.out.println("Node Type" + data.getNodeType());
				System.out.println("Node Type" + data.getNodeValue());
				if (data.getNodeName().equals(tagName)) {
					Node child = data.getChildNodes().item(0);
					System.out.println("Text Node Item" + child);
					System.out.println("Node Type" + child.getNodeType());
					System.out.println("Node Type" + child.getNodeValue());
					if (child.getNodeType() == Node.TEXT_NODE) {
						System.out.println("Value" + value);
						child.setNodeValue(value);
						System.out.println("Found & set the value");
						// return;
					}
				}

			}

			/*
			 * for (int i = 0; i < nodes.getLength(); i++) { Node node =
			 * nodes.item(i);
			 * 
			 * setNodeValue if (node.getNodeType() == Node.ELEMENT_NODE) {
			 * Element element = (Element) node; System.out.println("FundID: " +
			 * getValue("externalFundsId", element)); System.out.println(
			 * "Eff Date: " + getValue("effectiveDate", element));
			 * System.out.println("Company Code " + getValue("companyCode",
			 * element)); } }
			 */

			/*
			 * Node node = getNode("arAccountId", nodes); System.out.println(
			 * "Got Node:"+node); if ( node == null ) return;
			 * 
			 * // Locate the child text node and change its value NodeList
			 * childNodes = node.getChildNodes(); for (int y = 0; y <
			 * childNodes.getLength(); y++ ) { System.out.println("In loop");
			 * Node data = childNodes.item(y); if ( data.getNodeType() ==
			 * Node.TEXT_NODE ) { data.setNodeValue(value); System.out.println(
			 * "Found & set the value"); return; } }
			 */
			// Write updated XML
			/*
			 * doc = documentBuilder.parse(xmlFile); ; OutputFormat format = new
			 * OutputFormat(doc); format.setIndenting(true); String filename =
			 * xmlFile+System.currentTimeMillis()+".xml"; XMLSerializer
			 * serializer = new XMLSerializer( new FileOutputStream(new
			 * File(filename)), format); serializer.serialize(doc);
			 */

			DOMSource domSource = new DOMSource(doc);
			StreamResult streamResult = new StreamResult("C:/PER999/DataPool/PREMIUM_BILLING_2_5_001.xml"); // new
																											// File(
			// "createdFiles/createFile.xml")

			transformer.transform(domSource, streamResult);

			System.out.println("File saved to specified path!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();
	}

	@SuppressWarnings("unused")
	private static Node getNode(String tagName, NodeList nodes) {
		for (int x = 0; x < nodes.getLength(); x++) {
			System.out.println("NodeLenght" + nodes.getLength());
			Node node = nodes.item(x);
			System.out.println("Node:" + node);
			System.out.println("Node Name:" + node.getNodeName());
			if (node.getNodeName().equalsIgnoreCase(tagName)) {
				return node;
			}
		}

		return null;
	}

	public void testMain(Object[] args) {
		xmlRead("arAccountId", "824");
	}
}
