package com.pascalhow.screenGrab;


import com.pascalhow.constants.Constants;
import com.pascalhow.models.Course;
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

    private ArrayList<Course> courseList = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        String url = Constants.COURSE_URL;

        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla")
                .get();

        Elements paragraphs = doc.select("td");

        ArrayList<Course> courseList = buildCourseList(paragraphs);

        for(Course course : courseList) {
            System.out.println(course.toString());
        }
    }

    private static ArrayList<Course> buildCourseList(Elements paragraphs) {

        String code = "";
        String title;

        ArrayList<Course> courseList = new ArrayList<>();

        for (Element p : paragraphs) {
            if (!(p.text().contains("Current") || p.text().contains("Refresh information"))) {

                //  If p.ownText() is empty, it means we are reading the course code
                if((p.ownText().isEmpty()) && (p.text().length() >= 8)) {
                    code = p.text().substring(0, 8);
                } else {
                    title = p.ownText();

                    Course course = new Course.Builder()
                            .setCode(code)
                            .setTitle(title)
                            .build();

                    //  We now have both code and title for a given course so add to the list
                    courseList.add(course);
                }
            }
        }

        return courseList;
    }

//    private static List<String> getStringsFromUrl(String url, String cssQuery) throws IOException {
//        Document document = Jsoup.connect(url).get();
//        Elements elements = StringUtil.isBlank(cssQuery) ?
//                document.getElementsByClass("body") : document.select(cssQuery);
//
//        List<String> strings = new ArrayList<String>();
//        elements.traverse(new TextNodeExtractor(strings));
//        return strings;
//    }
//
//    private static class TextNodeExtractor implements NodeVisitor {
//        private final List<String> strings;
//        private Course courseUnit;
//        private ArrayList<Course> courseList = new ArrayList<>();
//
//        public TextNodeExtractor(List<String> strings) {
//            this.strings = strings;
//        }
//
//        @Override
//        public void head(Node node, int depth) {
//
//            if (node instanceof TextNode) {
//
//                TextNode textNode = ((TextNode) node);
//                String text = textNode.text();
//
//                if (!StringUtil.isBlank(text)) {
//                    strings.add(text);
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
