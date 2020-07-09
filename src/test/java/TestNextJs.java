import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author 最爱吃小鱼
 */
public class TestNextJs {

    @Test
    public void nextJStoString() throws IOException {
        List<String> lines = FileUtils.readLines(new java.io.File("src/main/resources/testScroll.html"),"UTF-8");

        for (int i = 0, size = lines.size() ; i < size ; i++) {
            if (i > 9) {
                String str = lines.get(i).replaceAll("MzUyNzE4MDM2MA==", "'+biz+'");
                str = str.replaceAll("</", "<\\\\/");
                System.out.println(String.format("nextjs += '%s';", str));
            }
        }
    }



    @Test
    public void nextJStoString_() throws IOException {
        List<String> lines = FileUtils.readLines(new java.io.File("src/main/resources/sss.ss.html"),"UTF-8");

        for (int i = 0, size = lines.size() ; i < size ; i++) {
            if (i > 9) {
                String str = lines.get(i).replaceAll("MzUyNzE4MDM2MA==", "'+biz+'");
                str = str.replaceAll("</", "<\\\\/");
                System.out.println(String.format("nextjs += '%s';", str));
            }
        }
    }
}
