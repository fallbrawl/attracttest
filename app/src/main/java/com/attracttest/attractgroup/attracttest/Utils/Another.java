package com.attracttest.attractgroup.attracttest.Utils;

import android.icu.util.ValueIterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.*;
import java.util.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by nexus on 05.09.2017.
 */
public class Another {
    public static void smth(){
        try {
            //File inputFile = new File("input.txt");
            String wow = "<rss xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:media=\"http://search.yahoo.com/mrss/\" version=\"2.0\">\n" +
                    "<channel>\n" +
                    "<title>Macworld</title>\n" +
                    "<link>https://www.macworld.com</link>\n" +
                    "<description/>\n" +
                    "<language>en-us</language>\n" +
                    "<pubDate>Tue, 05 Sep 2017 08:59:44 -0700</pubDate>\n" +
                    "<lastBuildDate>Tue, 05 Sep 2017 08:59:44 -0700</lastBuildDate>\n" +
                    "<item>\n" +
                    "<title>\n" +
                    "Tested: Galaxy Note 8 Live Focus vs iPhone 7 Plus Portrait Mode\n" +
                    "</title>\n" +
                    "<pubDate>Tue, 05 Sep 2017 08:05:00 -0700</pubDate>\n" +
                    "<author>Adam Patrick Murray</author>\n" +
                    "<dc:creator>Adam Patrick Murray</dc:creator>\n" +
                    "<description>\n" +
                    "<![CDATA[\n" +
                    "<article> <section class=\"page\"> <p>The Galaxy Note 8 is the first Samsung phone to feature a dual lens camera system. Similar to Apple’s iPhone 7 Plus, it includes a telephoto lens paired with a standard lens. This allows both phones to deliver fun depth of field effects—but does one company do bokeh better? Let’s check out the differences between their approaches, and see if one phone can emerge victorious.</p><figure class=\"large \"><a class=\"zoom\" href=\"https://images.idgesg.net/images/article/2017/09/iphone7plussamsungnote8-100734595-orig.jpg\" rel=\"nofollow\"><img src=\"https://images.idgesg.net/images/article/2017/09/iphone7plussamsungnote8-100734595-large.jpg\" border=\"0\" alt=\"Apple iPhone 7 Plus and Samsung Galaxy Note 8\" width=\"700\" height=\"467\" data-imageid=\"100734595\" data-license=\"IDG\"/></a> <small class=\"credit\">Adam Patrick Murray/IDG</small> <figcaption> <p>Apple's iPhone 7 Plus came out late last year while Samsung's Galaxy Note 8 launches in September.</p><p class=\"jumpTag\"><a href=\"/article/3221403/android/galaxy-note-8-live-focus-vs-iphone-7-plus-portrait-mode.html#jump\">To read this article in full or to leave a comment, please click here</a></p></section></article>\n" +
                    "]]>\n" +
                    "</description>\n" +
                    "<link>\n" +
                    "https://www.macworld.com/article/3221403/android/galaxy-note-8-live-focus-vs-iphone-7-plus-portrait-mode.html#tk.rss_all\n" +
                    "</link>\n" +
                    "<enclosure url=\"https://images.idgesg.net/images/article/2017/09/mvi_0224.00_00_15_15.still001-100734598-large.3x2.jpg\" length=\"0\" type=\"image/jpeg\"/>\n" +
                    "<categories>\n" +
                    "<category>Android</category>\n" +
                    "<category>iOS</category>\n" +
                    "<category>iPhone/iPad</category>\n" +
                    "<category>Photography</category>\n" +
                    "</categories>\n" +
                    "</item>\n" +
                    "<item>\n" +
                    "<title>\n" +
                    "Astell&Kern AK XB10 Bluetooth DAC and amp review: Wireless, hi-res audio for any headphones\n" +
                    "</title>\n" +
                    "<pubDate>Tue, 05 Sep 2017 04:30:00 -0700</pubDate>\n" +
                    "<author>Theo Nicolakis</author>\n" +
                    "<dc:creator>Theo Nicolakis</dc:creator>\n" +
                    "<description>\n" +
                    "You don't need new headphones to benefit from Qualcomm's aptX HD codec.\n" +
                    "</description>\n" +
                    "<link>\n" +
                    "https://www.techhive.com/article/3221402/bluetooth/astellkern-ak-xb10-review.html#tk.rss_all\n" +
                    "</link>\n" +
                    "<enclosure url=\"https://images.idgesg.net/images/article/2017/09/lg_01-100734579-large.3x2.jpg\" length=\"0\" type=\"image/jpeg\"/>\n" +
                    "<categories>\n" +
                    "<category>Bluetooth</category>\n" +
                    "<category>Audio</category>\n" +
                    "<category>Consumer Electronics</category>\n" +
                    "</categories>\n" +
                    "</item>";
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(wow);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("student");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("Student roll no : "
                            + eElement.getAttribute("rollno"));
                    System.out.println("First Name : "
                            + eElement
                            .getElementsByTagName("firstname")
                            .item(0)
                            .getTextContent());
                    System.out.println("Last Name : "
                            + eElement
                            .getElementsByTagName("lastname")
                            .item(0)
                            .getTextContent());
                    System.out.println("Nick Name : "
                            + eElement
                            .getElementsByTagName("nickname")
                            .item(0)
                            .getTextContent());
                    System.out.println("Marks : "
                            + eElement
                            .getElementsByTagName("marks")
                            .item(0)
                            .getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
