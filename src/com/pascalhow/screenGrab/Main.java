package com.pascalhow.screenGrab;


import com.pascalhow.constants.Constants;
import com.pascalhow.models.Units;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private ArrayList<Units> unitList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        String url = Constants.URL;

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla")
                .get();

        Elements paragraphs = doc.select("td");

        for (Element p : paragraphs) {
            if ((!p.text().contains("Current")) && (!p.text().contains("Refresh information"))) {

                Units unit = new Units.Builder()
                        .setCode(p.text().substring(0, p.text().indexOf(" ")))
                        .setTitle("")
                        .build();

                System.out.println("----- start -----");
                System.out.println(unit.getCode());
                System.out.println(p.text());
                System.out.println(p.ownText());
                System.out.println("----- end -----\n");
            }
        }
    }
//
//    private static List<String> getStringsFromUrl(String url, String cssQuery) throws IOException {
//        Document document = Jsoup.connect(url).get();
//        Elements elements = StringUtil.isBlank(cssQuery) ?
//                document.getElementsByTag("body") : document.select(cssQuery);
//
//        List<String> strings = new ArrayList<String>();
//        elements.traverse(new TextNodeExtractor(strings));
//        return strings;
//    }
//
//    private static class TextNodeExtractor implements NodeVisitor {
//        private final List<String> strings;
//        private Units courseUnit;
//        private ArrayList<Units> unitList = new ArrayList<>();
//
//        public TextNodeExtractor(List<String> strings) {
//            this.strings = strings;
//        }
//
//        @Override
//        public void head(Node node, int depth) {
//
//            String code = "";
//            String title = "";
//
//            if (node instanceof TextNode) {
//
//
//                TextNode textNode = ((TextNode) node);

//                if (depth == 13) {
//                    code = textNode.text();
//                }
//
//                if(depth == 14) {
//                    title = textNode.text();
//                }
//                unitList.add(new Units.Builder()
//                        .setCode(code)
//                        .setTitle(title)
//                        .build());
//
//                if (depth == 14) {
//                    String text = textNode.text();
//
//                    if (!StringUtil.isBlank(text)) {
//                        strings.add(text);
//                    }
//                }
//            }
//        }
//
//        @Override
//        public void tail(Node node, int depth) {}
//    }
//    public static void main(String[] args) {

//        Document doc;
//        try {
//
//            // need http protocol
//            doc = Jsoup.connect("http://training.gov.au/Training/Details/AHCAGB602")
//                    .userAgent("Mozilla")
//                    .get();
//
//            // get page title
//            String title = doc.title();
//            System.out.println("title : " + title);
//
//            // get all links
//            Elements links = doc.select("a[href]");
//            for (Element link : links) {
//
//                // get the value from href attribute
//                System.out.println("\nlink : " + link.attr("href"));
//                System.out.println("text : " + link.text());
//                System.out.println("body : " + link.getElementsByTag("body"));
//
//            }
//
//            System.out.println("text : " + doc);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

}
