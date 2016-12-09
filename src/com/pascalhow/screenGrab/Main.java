package com.pascalhow.screenGrab;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {
    
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

//        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
//        final HtmlPage page = webClient.getPage(url);
//        HtmlElement somePage = page.getElementByName("/Search/Training?IncludeSupersededData=false&amp;TypeAllTrainingComponents=false&amp;TypeTrainingPackages=false&amp;TypeQualifications=true%2Cfalse&amp;TypeAccreditedCourses=false&amp;TypeModule=false&amp;TypeUnitsOfCompetency=false&amp;TypeUnitContextualisations=false&amp;TypeSkillSets=false&amp;nrtSearchSubmit=Search&amp;AdvancedSearch=False&amp;JavaScriptEnabled=true&amp;educationLevel=-99&amp;recognisedby=-99&amp;tableResultsQualification-page=2&amp;setFocus=tableResultsQualification");
//        somePage.click();

        go();
        System.out.println("COMPLETE");
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

    private static void go() throws IOException {
        /* turn off annoying htmlunit warnings */
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);

        HtmlPage nextPage;
//        String url = "http://media.ethics.ga.gov/search/Campaign/Campaign_Name.aspx?NameID=5751&FilerID=C2009000085&Type=candidate";

        String url = Constants.COURSE_URL;

        final WebClient webclient = new WebClient(BrowserVersion.CHROME);
        final HtmlPage page = webclient.getPage(url);

        System.out.println("PULLING LINKS:");
        List<HtmlAnchor> articles = (List<HtmlAnchor>) page.getByXPath("//a[@class='t-link']");
        //List<HtmlAnchor> articles = (List<HtmlAnchor>) page.getByXPath("//div[@class='hform1']/a[@class='lblent
        // List<HtmlAnchor> articles = (List<HtmlAnchor>) page.getByXPath("//a[@class='lblentrylink']");rylink']");

        for(int x=0; x<articles.size(); x++) {
            if(articles.get(x).asText().contains("Navigate to page")) {
                System.out.println("Clicking " + articles.get(x).asText());
            }
//            nextPage = articles.get(x).click();
//            System.out.println(nextPage.getBody());
        }
    }
}
