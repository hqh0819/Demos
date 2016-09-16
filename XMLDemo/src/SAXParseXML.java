import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by luca on 2016/9/16.
 */
public class SAXParseXML {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {

        for (Book b : SAXParseXML.parseXML("D:\\IdeaProjects\\Demos\\XMLDemo\\books.xml")) System.out.println(b);
    }

    static public List<Book> parseXML(String path) throws ParserConfigurationException, SAXException, IOException {
        SAXParser parst = SAXParserFactory.newInstance().newSAXParser();
        MySAXParseHandler ch = new MySAXParseHandler();
        parst.parse(new File(path), ch);
        return ch.getList();
    }
}

class Book {


    private int num;
    private String name, author;
    private double price;

    public Book() {

    }

    public Book(int num, String name, double price) {
        this.num = num;
        this.name = name;
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "num=" + num +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}

class MySAXParseHandler extends DefaultHandler {
    private List<Book> list;
    private String tag;
    private Book book;

    public List<Book> getList() {
        return list;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        list = new LinkedList<Book>();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (qName.equals("book")) {
            book = new Book();
            book.setNum(Integer.parseInt(attributes.getValue("num")));
        }

        tag = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("book")) {
            list.add(book);
            book = null;
        }
        tag = null;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String value = new String(ch, start, length);

        if (tag == null) return;

        if (tag.equals("name")) {
            book.setName(value);
        } else if (tag.equals("price")) {
            book.setPrice(Double.parseDouble(value));
        } else if (tag.equals("author")) {
            book.setAuthor(value);
        }

    }
}
