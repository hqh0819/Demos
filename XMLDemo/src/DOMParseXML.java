
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by luca on 2016/9/16.
 */
public class DOMParseXML {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new FileInputStream("D:\\IdeaProjects\\Demos\\XMLDemo\\books.xml"));
        Node ns = d.getDocumentElement();
        List<Book> list = new LinkedList<Book>();
        Book b;
        for (int i = 0; i < ns.getChildNodes().getLength(); i++) {
            Node n1 = ns.getChildNodes().item(i);
            if (n1.getNodeName().equals("book")) {
                b = new Book();
                b.setNum(Integer.parseInt(n1.getAttributes().getNamedItem("num").getNodeValue()));
                for (int j = 0; j < n1.getChildNodes().getLength(); j++) {
                    Node n2 = n1.getChildNodes().item(j);

                    if (n2.getNodeName().equals("name")) {
                        b.setName(n2.getTextContent());
                    } else if (n2.getNodeName().equals("price")) {
                        b.setPrice(Double.parseDouble(n2.getTextContent()));
                    } else if (n2.getNodeName().equals("author")) {
                        b.setAuthor(n2.getTextContent());
                    }

                }
                list.add(b);

            }

        }

        for (int i = 0; i < list.size(); i++) {
System.out.println(list.get(i));
        }


    }
}
