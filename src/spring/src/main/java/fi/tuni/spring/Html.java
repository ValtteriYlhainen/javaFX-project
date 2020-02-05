package fi.tuni.spring;

import java.util.ArrayList;

/**
 * The Html-class contains all of the methods concerning
 * the html-ArrayList, which contains the html-code.
 */

public class Html {

    private ArrayList<String> html;

    /**
     * Initialises the html-code and adds all of the basic components.
     */

    public Html() {
        html = new ArrayList<>();
        html.add("<!DOCTYPE html> ");
        html.add("<html>");
        html.add("");
        html.add("<head>");
        html.add("<title></title>");
        html.add("</head>");
        html.add("");
        html.add("<body>");
    }

    /**
     * Returns the html-ArrayList
     *
     * @return
     */
    public ArrayList<String> getHtml() {
        return html;
    }

    /**
     * Sets the html-ArrayList
     *
     * @param html
     */
    public void setHtml(ArrayList<String> html) {
        this.html = html;
    }

    /**
     * Returns the html-ArrayList in String-form.
     *
     * @return
     */
    public String getHtmlString() {
        String htmlOutput = "";
        for (String s : html) {
            htmlOutput += s + "\n";
        }
       return htmlOutput += "</body>\n</html>";
    }

    /**
     *Prints html-code to console for test purposes.
     */
    public void printHtml() {
        html.forEach(System.out::println);
        System.out.println("</body>");
        System.out.println("</html>");
    }

}
