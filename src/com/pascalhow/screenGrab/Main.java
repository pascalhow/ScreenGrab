package com.pascalhow.screenGrab;


import com.oracle.tools.packager.Log;
import com.pascalhow.constants.Constants;
import com.pascalhow.models.Course;
import com.pascalhow.models.Criteria;
import com.pascalhow.models.PerformanceCriteria;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    private ArrayList<Course> courseList = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        String url = Constants.COURSE_URL;
        // Scan first page to get qualifications
        ArrayList<Course> qualificationList = scanPageForCourses(url, "#resultsBodyQualification");

        for(Course course : qualificationList) {
            // iterate through list to get the second pages,which list the units
            ArrayList<Course> unitList = scanPageForCourses(course.getLink(),"#tableUnits");
            for(Course unit : unitList) {
                // The last page where it dispalys the performance and criteria pages
                ArrayList<PerformanceCriteria> criterasList = scanPageForPerformanceCriteria(unit.getLink());
                System.out.println(criterasList.toString());
            }
        }
    }

    private static ArrayList<Course> scanPageForCourses(String url, String table) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Chrome")
                    .get();

            // only get from the resultant table, with ID resultsBodyQualification, and in the body element, ignore header and footer
            Elements paragraphs = doc.select(table + " tbody tr");

            ArrayList<Course> courseList = buildCourseList(paragraphs);

            return courseList;
        }
        catch(IOException error) {
            // If it fails it would throw an error and return an empty array
            Log.debug("failed to scan page");
            return new ArrayList<Course>();
        }
    }

    private static ArrayList<Course> buildCourseList(Elements rows) {

        ArrayList<Course> courseList = new ArrayList<>();

        // Build the court list based on the tables
        for (Element r : rows) {
            Element firstCol = r.child(0);
            Element secondCol = r.child(1);
            String code = firstCol.child(0).ownText();
            String link = firstCol.child(0).attr("href").toString();
            String title = secondCol.ownText();

            Course course = new Course.Builder()
                    .setCode(code)
                    .setTitle(title)
                    .setLink(link)
                    .build();

            //  We now have both code and title for a given course so add to the list
            courseList.add(course);

        }

        return courseList;
    }

    private static ArrayList<PerformanceCriteria> scanPageForPerformanceCriteria(String url) {
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Chrome")
                    .get();


            Elements tables = doc.select(".ait-table tbody");
            ArrayList<PerformanceCriteria> criteriasList = new ArrayList<PerformanceCriteria>();

            for (Element table : tables) {
                // iterating through each table to find the performance criteria table.
                // the only way is to check the table
                // the first child returns the row
                Element rows = table.child(0);
                // Get the first column in the row, by getting the child of the row
                Element firstCol = rows.child(0);

                // Remove everything except for alphabets. It has a space, that needs to be removed, to compare the words
                String firstColString = firstCol.text().replaceAll("[^A-Za-z]+", "");
                // Get the content of the first column, if it matches element, means it the correct table
                if(firstColString.equalsIgnoreCase("element")) {
                    // Get the second column in the row
                    String secondColString = rows.child(1).text().replaceAll("[^A-Za-z]+", "");
                    if(secondColString.equalsIgnoreCase("PERFORMANCECRITERIA")) {
                        criteriasList = buildPerformanceCriteriaList(table);
                    }

                }

            }



            return criteriasList;
        }
        catch(IOException error) {
            Log.debug("failed to scan page");
            return new ArrayList<PerformanceCriteria>();
        }
    }

    private static ArrayList<PerformanceCriteria> buildPerformanceCriteriaList(Element table) {

        ArrayList<PerformanceCriteria> elementsCrtieriasList = new ArrayList<>();

        Elements rows = table.children();
        for (int i = 1; i < rows.size(); i ++) {
            // get first column's text
//            rows.get(i).child(0)
            Elements secondColumnArray = rows.get(i).child(1).children();
            ArrayList<String> performances = new ArrayList<String>();
            for (Element item: secondColumnArray) {
                performances.add(item.text());
            }
            Criteria criteria = new Criteria(rows.get(i).child(0).text(), performances);

            PerformanceCriteria performanceCriteria = new PerformanceCriteria.Builder()
                    .addCriteria(criteria)
                    .build();

            System.out.print(criteria.toString());
            //  We now have both code and title for a given course so add to the list
            elementsCrtieriasList.add(performanceCriteria);
        }


        return elementsCrtieriasList;
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
