package com.seven.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

/**
 * 把html格式内容导出pdf文档
 *
 * @Author guost
 * @Date 2020/7/9 上午11:39
 * @Version 1.0.0
 */
public class PdfUtil {
    private static Logger log = LoggerFactory.getLogger(PdfUtil.class);
    /**
     * 生成Pdf
     *
     * @param fileName
     * @param html
     */
    public static void createPDF(String fileName, String html) {
        OutputStream os = null;
        ITextRenderer renderer = new ITextRenderer();
        try {
            os = new FileOutputStream(fileName);

            ITextFontResolver fontResolver = renderer.getFontResolver();
            //指定字体。为了支持中文字体
            fontResolver.addFont("fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            StringBuilder content = new StringBuilder("<!DOCTYPE html><html lang=\"en\"><head>");
            content.append("<meta charset=\"UTF-8\"></meta><style type=\"text/css\">");
            content.append("body { font-family: simhei; }</style></head><body style =\"font-family: SimHei; font-size:small;\">");
            content.append(html);
            content.append("</body></html>");

            log.info("输出的网页= {}", content.toString());

            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(os);
            renderer.finishPDF();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                renderer = null;
                if (os != null) {
                    os.close();
                }
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
        }
    }

    /**
     *
     * @param url 文件的url地址
     * @param pdfFile  生成的pdf文件存储路径
     * @param chineseFontPath 中文字体存储路径
     */
    public static void html2pdf(String url, String pdfFile, String chineseFontPath)  {
        // step 1
        OutputStream os = null;
        try {
            os = new FileOutputStream(pdfFile);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);
            // 解决中文不显示问题
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont("fonts/simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            renderer.layout();
            renderer.createPDF(os);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if(os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        String html = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    </meta>\n" +
                "    <style type=\"text/css\">\n" +
                "    body {\n" +
                "        font-family: simhei;\n" +
                "    }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body style=\"font-family: SimHei; font-size:small;\">\n" +
                "    <section data-tools=\"135编辑器\" data-id=\"95966\" style=\"max-width: 100%;font-family: -apple-system-font, system-ui, Arial, sans-serif;letter-spacing: 0.544px;white-space: normal;background-color: rgb(255, 255, 255);font-size: 16px;box-sizing: border-box !important;overflow-wrap: break-word !important;\" data-mpa-powered-by=\"yiban.io\">\n" +
                "        <section data-tools=\"135编辑器\" data-id=\"92987\" style=\"max-width: 100%;box-sizing: border-box !important;overflow-wrap: break-word !important;\">\n" +
                "            <section data-width=\"100%\" style=\"padding-top: 20px;padding-bottom: 20px;max-width: 100%;box-sizing: border-box;width: 578px;text-align: center;overflow-wrap: break-word !important;\">\n" +
                "                <section style=\"max-width: 100%;display: inline-block;box-sizing: border-box !important;overflow-wrap: break-word !important;\">\n" +
                "                    <section style=\"max-width: 100%;display: inline-block;box-sizing: border-box !important;overflow-wrap: break-word !important;\">\n" +
                "                        <section data-width=\"100%\" style=\"max-width: 100%;width: 364px;box-sizing: border-box !important;overflow-wrap: break-word !important;\">\n" +
                "                            <section style=\"padding-right: 0.5em;padding-left: 0.5em;max-width: 100%;background: rgb(255, 201, 15);color: rgb(0, 0, 0);border-radius: 25px;justify-content: center;display: -webkit-flex;align-items: center;box-sizing: border-box !important;overflow-wrap: break-word !important;\">\n" +
                "                                <section data-brushtype=\"text\" style=\"padding-right: 0.5em;padding-left: 0.5em;max-width: 100%;box-sizing: border-box;height: 35px;display: inline-block;line-height: 35px;letter-spacing: 1px;font-weight: bold;overflow-wrap: break-word !important;\"><span style=\"max-width: 100%;box-sizing: border-box !important;overflow-wrap: break-word !important;\">每天早上<span style=\"max-width: 100%;font-size: 28px;box-sizing: border-box !important;overflow-wrap: break-word !important;\">七点三十</span></span>，准时推送干货</section>\n" +
                "                            </section>\n" +
                "                        </section>\n" +
                "                    </section>\n" +
                "                </section>\n" +
                "            </section>\n" +
                "        </section>\n" +
                "    </section>\n" +
                "    <section data-role=\"paragraph\" style=\"max-width: 100%;font-family: -apple-system-font, " +
                "system-ui, Arial, sans-serif;letter-spacing: 0.544px;white-space: normal;background-color: rgb(255, 255, 255);box-sizing: border-box !important;overflow-wrap: break-word !important;\">\n" +
                "        <p style=\"max-width: 100%;min-height: 1em;text-align: center;box-sizing: border-box " +
                "!important;overflow-wrap: break-word !important;\"><br style=\"max-width: 100%;box-sizing: " +
                "border-box !important;overflow-wrap: break-word !important;\" /></p>\n" +
                "        <p style=\"text-align: center;\"><img class=\"rich_pages\" data-ratio=\"0.6666666666666666\"" +
                " data-s=\"300,640\" data-src=\"https://mmbiz.qpic" +
                ".cn/mmbiz_png/laEmibHFxFw66icfQluCyicFdntO4RkwicZYiaX0L3Jl8AuFUVWVqHrp3CfbBgJqW8zqh7vnLzVEuedf8PQJZRSpCoA/640?wx_fmt=png\" data-type=\"png\" data-w=\"900\" /></p>\n" +
                "    </section>\n" +
                "    <p><br></p>\n" +
                "    <section data-tool=\"mdnice编辑器\" data-website=\"https://www.mdnice.com\" style=\"padding-right: 10px;padding-left: 10px;overflow-wrap: break-word;text-align: left;font-family: Optima-Regular, Optima, PingFangSC-light, PingFangTC-light,  Cambria, Cochin, Georgia, Times, \"Times New Roman\" , serif;margin-top: -10px;line-height: 1.6;letter-spacing: 0.034em;color: rgb(63, 63, 63);font-size: 16px;\">\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">前几天阿粉的一个朋友去面试，面试官问他，你知道SpringMVC的执行流程么，我这个朋友在回答完之后，面试官相继问了几个问题，之后面试官说，兄弟你是培训出来的吧？朋友懵了，我培训都是一年前的事情了，这都能知道，于是，找阿粉来吐槽这个事情，结果，阿粉听完之后，分分钟觉得，确实不冤枉呀。</p>\n" +
                "        <h2 data-tool=\"mdnice编辑器\" style=\"font-weight: bold;color: black;font-size: 22px;text-align: center;background-image: url(\" https://mmbiz.qpic.cn/mmbiz_png/laEmibHFxFw66fvRb91LzDVh1v6gXSJGtrV3XhBzTbMwArZ6tdiaUiaBRgmhLcXd1pebicWXcNia3DAiayibULicgNloAA/640?wx_fmt=png\");background-position: center center;background-repeat: no-repeat;background-attachment: initial;background-origin: initial;background-clip: initial;background-size: 50px;margin-top: 1em;margin-bottom: 10px;\"><span style=\"display: none;\"></span><span style=\"display: inline-block;height: 38px;line-height: 42px;color: rgb(72, 179, 120);background-position: left center;background-repeat: no-repeat;background-attachment: initial;background-origin: initial;background-clip: initial;background-size: 63px;margin-top: 38px;font-size: 18px;margin-bottom: 10px;\">SpringMVC的执行流程</span></h2>\n" +
                "        <p style=\"text-align: center;\"><img class=\"rich_pages js_insertlocalimg\" data-ratio=\"0" +
                ".5625\" data-s=\"300,640\" data-src=\"https://mmbiz.qpic" +
                ".cn/mmbiz_jpg/laEmibHFxFw66fvRb91LzDVh1v6gXSJGtzvicrF7Kia9uv0Z65fvN9BML5xdYBT37SjAF3iaic8zHW4WHxabic6cKuWg/640?wx_fmt=jpeg\" data-type=\"jpeg\" data-w=\"1280\" /></p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">大家看这个图，确实是没有任何问题的对不对，</p>\n" +
                "        <ol data-tool=\"mdnice编辑器\" style=\"margin-top: 8px;margin-bottom: 8px;padding-left: 25px;color: black;\" class=\"list-paddingleft-2\">\n" +
                "            <li>\n" +
                "                <section style=\"margin-top: 5px;margin-bottom: 5px;line-height: 26px;color: rgb(1, 1, 1);\">\n" +
                "                    <p style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">用户的 HTTP 的请求提交到 DispatcherServlet。</p>\n" +
                "                </section>\n" +
                "            </li>\n" +
                "            <li>\n" +
                "                <section style=\"margin-top: 5px;margin-bottom: 5px;line-height: 26px;color: rgb(1, 1, 1);\">\n" +
                "                    <p style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">由 DispatcherServlet 控制器查询一个或多个 HandlerMapping，找到处理请求的Controller。</p>\n" +
                "                </section>\n" +
                "            </li>\n" +
                "            <li>\n" +
                "                <section style=\"margin-top: 5px;margin-bottom: 5px;line-height: 26px;color: rgb(1, 1, 1);\">\n" +
                "                    <p style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">DispatcherServlet 将请求提交到 Controller，Controller 调用业务逻辑处理后，返回 ModelAndView</p>\n" +
                "                </section>\n" +
                "            </li>\n" +
                "            <li>\n" +
                "                <section style=\"margin-top: 5px;margin-bottom: 5px;line-height: 26px;color: rgb(1, 1, 1);\">\n" +
                "                    <p style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">业务逻辑处理完了，这时候DispatcherServlet 查询 ModelAndView</p>\n" +
                "                </section>\n" +
                "            </li>\n" +
                "            <li>\n" +
                "                <section style=\"margin-top: 5px;margin-bottom: 5px;line-height: 26px;color: rgb(1, 1, 1);\">\n" +
                "                    <p style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">DispatcherServlet 查询一个或多个 ViewResoler 视图解析器，找到 ModelAndView 指定的视图。</p>\n" +
                "                </section>\n" +
                "            </li>\n" +
                "            <li>\n" +
                "                <section style=\"margin-top: 5px;margin-bottom: 5px;line-height: 26px;color: rgb(1, 1, 1);\">\n" +
                "                    <p style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">这时候就把这个 ModelAndView解析之后反馈给浏览器。</p>\n" +
                "                </section>\n" +
                "            </li>\n" +
                "            <li>\n" +
                "                <section style=\"margin-top: 5px;margin-bottom: 5px;line-height: 26px;color: rgb(1, 1, 1);\">\n" +
                "                    <p style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">Http 响应：视图负责将结果显示到客户端</p>\n" +
                "                </section>\n" +
                "            </li>\n" +
                "        </ol>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">这时候有的面试官就会问你了，说如果说我不想经过视图解析器用什么注解，那不经过视图解析器的话，那么返回的数据就是 Json 了，这个大家肯定熟悉，直接回答 @ResponseBody 就可以了。</p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">这部分内容很多的培训机构都会教给学员们去背诵，而不是如何的去理解一下，如果不往继续深挖的话，这块内容直接就过了，但是很多稍微大一点的“厂子”肯定会继续往下说，比如说：</p>\n" +
                "        <ul data-tool=\"mdnice编辑器\" style=\"margin-top: 8px;margin-bottom: 8px;padding-left: 25px;color: black;\" class=\"list-paddingleft-2\">\n" +
                "            <li>\n" +
                "                <section style=\"margin-top: 5px;margin-bottom: 5px;line-height: 26px;color: rgb(1, 1, 1);\">那你说说SpringMVC的工作机制吧，这时候在朋友们的心中会有个大大的懵，机制？原理？机制和原理有啥不一样的呢？<span style=\"text-align: center;color: rgb(63, 63, 63);letter-spacing: 0.034em;\"></span></section>\n" +
                "            </li>\n" +
                "        </ul>\n" +
                "        <h2 data-tool=\"mdnice编辑器\" style=\"font-weight: bold;color: black;font-size: 22px;text-align: center;background-image: url(\" https://mmbiz.qpic.cn/mmbiz_png/laEmibHFxFw66fvRb91LzDVh1v6gXSJGtrV3XhBzTbMwArZ6tdiaUiaBRgmhLcXd1pebicWXcNia3DAiayibULicgNloAA/640?wx_fmt=png\");background-position: center center;background-repeat: no-repeat;background-attachment: initial;background-origin: initial;background-clip: initial;background-size: 50px;margin-top: 1em;margin-bottom: 10px;\"><span style=\"display: none;\"></span><span style=\"display: inline-block;height: 38px;line-height: 42px;color: rgb(72, 179, 120);background-position: left center;background-repeat: no-repeat;background-attachment: initial;background-origin: initial;background-clip: initial;background-size: 63px;margin-top: 38px;font-size: 18px;margin-bottom: 10px;\">SpringMVC的工作机制</span></h2>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">对于大家来说，SpringMVC的执行流程大家肯定都熟悉了，这个肯定大家回答的也会很完美，那么接下来就看看机制的问题吧，</p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">SpringMVC框架其实围绕的都是 DispatcherServlet 来工作的，这个类也尤其的重要，其实看到名字的时候，阿粉第一想法就是，它是不是一个另类的 Servlet，而学习过 Java 的我们当然也知道 Servlet 可以拦截到 HTTP 发送过来的请求。</p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">而我们的 Servlet 在初始化的时候，也就是在调用 init 方法的时候，SpringMVC 会根据配置，来获取配置信息，从而来获得 URI 和处理器 Handler 之间的映射关系，而这个URI 是统一资源标识符。为了更加灵活的操作和增强某些我们所需要的功能，这时候，SpringMVC还会给处理器加入拦截器。</p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">而SpringMVC的容器初始化的时候，会建立所有url和controller的对应关系，</p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\"><strong style=\"line-height: 1.75em;\">ApplicationObjectSupport</strong> 里面内容比较多，源码部分我精简了一下</p>\n" +
                "        <pre data-tool=\"mdnice编辑器\" style=\"margin-top: 10px;margin-bottom: 10px;border-radius: 5px;box-shadow: rgba(0, 0, 0, 0.55) 0px 2px 10px;\"><span style=\"display: block;background: url(\"https://mmbiz.qpic.cn/mmbiz_png/laEmibHFxFw66fvRb91LzDVh1v6gXSJGtKeGiaj8j3ZYM4q47ycvSY8xic91u5F9G5ReP017ScFz627cWFpfVqlrA/640?wx_fmt=png\") 10px 10px / 40px no-repeat rgb(30, 30, 30);height: 30px;width: 100%;margin-bottom: -7px;border-radius: 5px;\"></span><code style=\"overflow-x: auto;padding: 16px;color: #DCDCDC;display: -webkit-box;font-family: Operator Mono, Consolas, Monaco, Menlo, monospace;font-size: 12px;-webkit-overflow-scrolling: touch;padding-top: 15px;background: #1E1E1E;border-radius: 5px;\">@Override<br> public final void setApplicationContext(@Nullable ApplicationContext context) throws BeansException {<br>  <span style=\"color: #569CD6;line-height: 26px;\">else</span> <span style=\"color: #569CD6;line-height: 26px;\">if</span> (this.applicationContext == null) {<br>   // Initialize with passed-in context.<br>   <span style=\"color: #569CD6;line-height: 26px;\">if</span> (!requiredContextClass().isInstance(context)) {<br>    throw new ApplicationContextException(<br>      <span style=\"color: #D69D85;line-height: 26px;\">\"Invalid application context: needs to be of type [\"</span> + requiredContextClass().getName() + <span style=\"color: #D69D85;line-height: 26px;\">\"]\"</span>);<br>   }<br>   this.applicationContext = context;<br>   this.messageSourceAccessor = new MessageSourceAccessor(context);<br>   initApplicationContext(context);<br>  }<br> }<br> 此处注意的是initApplicationContext(context);<br>    这个方法在子类中实现了 ：子类AbstractDetectingUrlHandlerMapping实现了该方法<br>   <br></code></pre>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">子类 <strong style=\"line-height: 1.75em;\">AbstractDetectingUrlHandlerMapping</strong></p>\n" +
                "        <pre data-tool=\"mdnice编辑器\" style=\"margin-top: 10px;margin-bottom: 10px;border-radius: 5px;box-shadow: rgba(0, 0, 0, 0.55) 0px 2px 10px;\"><span style=\"display: block;background: url(\"https://mmbiz.qpic.cn/mmbiz_png/laEmibHFxFw66fvRb91LzDVh1v6gXSJGtKeGiaj8j3ZYM4q47ycvSY8xic91u5F9G5ReP017ScFz627cWFpfVqlrA/640?wx_fmt=png\") 10px 10px / 40px no-repeat rgb(30, 30, 30);height: 30px;width: 100%;margin-bottom: -7px;border-radius: 5px;\"></span><code style=\"overflow-x: auto;padding: 16px;color: #DCDCDC;display: -webkit-box;font-family: Operator Mono, Consolas, Monaco, Menlo, monospace;font-size: 12px;-webkit-overflow-scrolling: touch;padding-top: 15px;background: #1E1E1E;border-radius: 5px;\"> protected void detectHandlers() throws BeansException {<br>     ApplicationContext applicationContext = obtainApplicationContext();<br>     String[] beanNames = (this.detectHandlersInAncestorContexts ?<br>       BeanFactoryUtils.beanNamesForTypeIncludingAncestors(applicationContext, Object.class) :<br>       applicationContext.getBeanNamesForType(Object.class));<br>   <br>     // 采取任何bean的名字,我们可以确定url。.<br>     <span style=\"color: #569CD6;line-height: 26px;\">for</span> (String beanName : beanNames) {<br>      String[] urls = determineUrlsForHandler(beanName);<br>      <span style=\"color: #569CD6;line-height: 26px;\">if</span> (!ObjectUtils.isEmpty(urls)) {<br>       // URL路径发现:我们认为这是一个处理程序 这时候就要保存urls和beanName的对应关系,<br>       registerHandler(urls, beanName);<br>      }<br>     }<br>   <br>     <span style=\"color: #569CD6;line-height: 26px;\">if</span> ((logger.isDebugEnabled() && !getHandlerMap().isEmpty()) || logger.isTraceEnabled()) {<br>      logger.debug(<span style=\"color: #D69D85;line-height: 26px;\">\"Detected \"</span> + getHandlerMap().size() + <span style=\"color: #D69D85;line-height: 26px;\">\" mappings in \"</span> + formatMappingName());<br>     }<br>    }<br>   <br>   通过父类的registerHandler给put到HandlerMap里面了<br></code></pre>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">而我们在使用SpringMVC的Controller里面的注解解析 Url 的时候，通过的是什么类？什么方法呢？就是接下来的这个方法，大家可以看注释</p>\n" +
                "        <pre data-tool=\"mdnice编辑器\" style=\"margin-top: 10px;margin-bottom: 10px;border-radius: 5px;box-shadow: rgba(0, 0, 0, 0.55) 0px 2px 10px;\"><span style=\"display: block;background: url(\"https://mmbiz.qpic.cn/mmbiz_png/laEmibHFxFw66fvRb91LzDVh1v6gXSJGtKeGiaj8j3ZYM4q47ycvSY8xic91u5F9G5ReP017ScFz627cWFpfVqlrA/640?wx_fmt=png\") 10px 10px / 40px no-repeat rgb(30, 30, 30);height: 30px;width: 100%;margin-bottom: -7px;border-radius: 5px;\"></span><code style=\"overflow-x: auto;padding: 16px;color: #DCDCDC;display: -webkit-box;font-family: Operator Mono, Consolas, Monaco, Menlo, monospace;font-size: 12px;-webkit-overflow-scrolling: touch;padding-top: 15px;background: #1E1E1E;border-radius: 5px;\">//确定给定的url处理器bean。(Determine the URLs <span style=\"color: #569CD6;line-height: 26px;\">for</span> the given handler bean.)<br>protected abstract String[] determineUrlsForHandler(String beanName);<br></code></pre>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">在我们日常写 CRUD 的时候，建立Controller的时候，在上面总是习惯的@RequestMapping注解，里面写我们从前端的ajax或者其他方式请求过来的路径的时候，通过这个方法来进行Controller和url之间的对应关系。这时候关系完成了，接下来肯定是根据url去找Controller，继续往下执行了呗。</p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">这时候就会执行你写的Controller方法，在我们的 Servlet里面是不是就相当于我们的 doService 的方法了，这一步阿粉就不仔细的给大家讲述了，大家可以参照　Servlet 来进行分析呢。</p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">最后一步来了，通过反射调用处理请求的方法，这时候给大家返回一个视图，也就是我们的 return。但是这个return也是有讲究的，JSP, JSON, Velocity, FreeMarker, XML, PDF, Excel, 还有Html字符流等等。那它们该如何的进行处理的呢？接下来阿粉就来带大家看一下</p>\n" +
                "        <p style=\"text-align: center;\"><img class=\"rich_pages js_insertlocalimg\" data-ratio=\"1.0125523012552302\" data-s=\"300,640\" data-src=\"https://mmbiz.qpic.cn/mmbiz_jpg/laEmibHFxFw66fvRb91LzDVh1v6gXSJGtP45J6Py9u6RcNInGiaupW0H7MLtXsHRrmQwWAJ3Q8PGk6huQ23axABw/640?wx_fmt=jpeg\" data-type=\"jpeg\" data-w=\"478\" style></p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">大家看一下这个图里面的 UrlBaseViewResolver ,类名真的是起的很有水准 <strong style=\"line-height: 1.75em;\">Url基础视图解析器</strong> 基础视图解析器，那么我们先说返回 JSP 的，配置如下：</p>\n" +
                "        <pre data-tool=\"mdnice编辑器\" style=\"margin-top: 10px;margin-bottom: 10px;border-radius: 5px;box-shadow: rgba(0, 0, 0, 0.55) 0px 2px 10px;\"><span style=\"display: block;background: url(\"https://mmbiz.qpic.cn/mmbiz_png/laEmibHFxFw66fvRb91LzDVh1v6gXSJGtKeGiaj8j3ZYM4q47ycvSY8xic91u5F9G5ReP017ScFz627cWFpfVqlrA/640?wx_fmt=png\") 10px 10px / 40px no-repeat rgb(30, 30, 30);height: 30px;width: 100%;margin-bottom: -7px;border-radius: 5px;\"></span><code style=\"overflow-x: auto;padding: 16px;color: #DCDCDC;display: -webkit-box;font-family: Operator Mono, Consolas, Monaco, Menlo, monospace;font-size: 12px;-webkit-overflow-scrolling: touch;padding-top: 15px;background: #1E1E1E;border-radius: 5px;\"><bean id=<span style=\"color: #D69D85;line-height: 26px;\">\"viewResolver\"</span> class=<span style=\"color: #D69D85;line-height: 26px;\">\"org.springframework.web.servlet.view.InternalResourceViewResolver\"</span>><br>    <property name=<span style=\"color: #D69D85;line-height: 26px;\">\"viewClass\"</span> value=<span style=\"color: #D69D85;line-height: 26px;\">\"org.springframework.web.servlet.view.JstlView\"</span>/><br>    <property name=<span style=\"color: #D69D85;line-height: 26px;\">\"prefix\"</span> value=<span style=\"color: #D69D85;line-height: 26px;\">\"/WEB-INF/jsp/\"</span>/><br>    <property name=<span style=\"color: #D69D85;line-height: 26px;\">\"suffix\"</span> value=<span style=\"color: #D69D85;line-height: 26px;\">\".jsp\"</span>/><br></bean><br></code></pre>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">相信项目中如果使用JSP的同志们去直接的配置文件去寻找这个，肯定不出意外的能找到，这时候我们return的一个字符串，通过配置，直接找寻指定的JSP页面，这也是最经常使用的一点了。如果我们返回的是我们的 test 页面，那么肯定是 <code style=\"font-size: 14px;overflow-wrap: break-word;padding: 2px 4px;border-radius: 4px;margin-right: 2px;margin-left: 2px;background-color: rgba(27, 31, 35, 0.05);font-family: \" Operator Mono\", Consolas, Monaco, Menlo, monospace;word-break: break-all;color: rgb(40, 202, 113);\">return \"test\"</code> ,然后结合上面的配置<property name=\"prefix\" value=\"/WEB-INF/jsp/\">和<property name=\"suffix\" value=\".jsp\">，最后得到最终的URL：\"/WEB-INF/jsp/\" + \"test\" + \".jsp\" == \"/WEB-INF/jsp/test.jsp\".</property>\n" +
                "            </property>\n" +
                "        </p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">那么HTML这种是怎么处理返回的呢？其实也很简单，之前阿粉就说过这个 SpringMVC 其实可以理解成 Servlet ，那么返回的方式就有了PrintWriter的事情了</p>\n" +
                "        <pre data-tool=\"mdnice编辑器\" style=\"margin-top: 10px;margin-bottom: 10px;border-radius: 5px;box-shadow: rgba(0, 0, 0, 0.55) 0px 2px 10px;\"><span style=\"display: block;background: url(\"https://mmbiz.qpic.cn/mmbiz_png/laEmibHFxFw66fvRb91LzDVh1v6gXSJGtKeGiaj8j3ZYM4q47ycvSY8xic91u5F9G5ReP017ScFz627cWFpfVqlrA/640?wx_fmt=png\") 10px 10px / 40px no-repeat rgb(30, 30, 30);height: 30px;width: 100%;margin-bottom: -7px;border-radius: 5px;\"></span><code style=\"overflow-x: auto;padding: 16px;color: #DCDCDC;display: -webkit-box;font-family: Operator Mono, Consolas, Monaco, Menlo, monospace;font-size: 12px;-webkit-overflow-scrolling: touch;padding-top: 15px;background: #1E1E1E;border-radius: 5px;\">StringBuffer sb = new StringBuffer();<br>sb.append(<span style=\"color: #D69D85;line-height: 26px;\">\"<!doctype html><html><head><meta http-equiv=\\\"Content-Type\\\" content=\\\"text/html; charset=UTF-8\\\">\"</span>)<br>sb.append(<span style=\"color: #D69D85;line-height: 26px;\">\"<div>xxxxxx</div>\"</span>)<br>writer.write(sb.toString());<br></code></pre>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">还有一个最常见的，返回JSON数据，那么Json数据我们最长用的，什么ajax这种来返回数据，使用各种UI的时候，也会让你返回JSON数据啦，这些东西都是必不可少呢，那么就像阿粉之前说的一个注解完事，如果有什么指定格式的，那么可以新建一个DTO的类，里面有你自己的属性，还可能带着你为了数据完整性而带上的数据比如List<t>这种。</t>\n" +
                "        </p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">而你说了这些之后，面试官顺带来了一句，Spring MVC的主要组件都有那些，你知道么？随便列举出几个来就行。</p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">SpringMVC的组件：</p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">1、前端控制器 DispatcherServlet</p>\n" +
                "        <ul data-tool=\"mdnice编辑器\" style=\"margin-top: 8px;margin-bottom: 8px;padding-left: 25px;color: black;\" class=\"list-paddingleft-2\">\n" +
                "            <li>\n" +
                "                <section style=\"margin-top: 5px;margin-bottom: 5px;line-height: 26px;color: rgb(1, 1, 1);\">作用：接收请求、响应结果 相当于转发器，有了DispatcherServlet 就减少了其它组件之间的耦合度。</section>\n" +
                "            </li>\n" +
                "        </ul>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">2、处理器映射器HandlerMapping</p>\n" +
                "        <ul data-tool=\"mdnice编辑器\" style=\"margin-top: 8px;margin-bottom: 8px;padding-left: 25px;color: black;\" class=\"list-paddingleft-2\">\n" +
                "            <li>\n" +
                "                <section style=\"margin-top: 5px;margin-bottom: 5px;line-height: 26px;color: rgb(1, 1, 1);\">作用：根据请求的URL来查找Handler</section>\n" +
                "            </li>\n" +
                "        </ul>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">3、处理器适配器HandlerAdapter</p>\n" +
                "        <ul data-tool=\"mdnice编辑器\" style=\"margin-top: 8px;margin-bottom: 8px;padding-left: 25px;color: black;\" class=\"list-paddingleft-2\">\n" +
                "            <li>\n" +
                "                <section style=\"margin-top: 5px;margin-bottom: 5px;line-height: 26px;color: rgb(1, 1, 1);\">注意：在编写Handler的时候要按照HandlerAdapter要求的规则去编写，这样适配器HandlerAdapter才可以正确的去执行Handler。</section>\n" +
                "            </li>\n" +
                "        </ul>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">4、处理器Handler</p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">5、视图解析器 ViewResolver</p>\n" +
                "        <ul data-tool=\"mdnice编辑器\" style=\"margin-top: 8px;margin-bottom: 8px;padding-left: 25px;color: black;\" class=\"list-paddingleft-2\">\n" +
                "            <li>\n" +
                "                <section style=\"margin-top: 5px;margin-bottom: 5px;line-height: 26px;color: rgb(1, 1, 1);\">作用：进行视图的解析 根据视图逻辑名解析成真正的视图（view）</section>\n" +
                "            </li>\n" +
                "        </ul>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">6、视图View</p>\n" +
                "        <ul data-tool=\"mdnice编辑器\" style=\"margin-top: 8px;margin-bottom: 8px;padding-left: 25px;color: black;\" class=\"list-paddingleft-2\">\n" +
                "            <li>\n" +
                "                <section style=\"margin-top: 5px;margin-bottom: 5px;line-height: 26px;color: rgb(1, 1, 1);\">View是一个接口， 它的实现类支持不同的视图类型（jsp，freemarker，pdf, json等等）</section>\n" +
                "            </li>\n" +
                "        </ul>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">关于SpringMVC的高频面试，你会了么？</p>\n" +
                "        <h2 data-tool=\"mdnice编辑器\" style=\"font-weight: bold;color: black;font-size: 22px;text-align: center;background-image: url(\" https://mmbiz.qpic.cn/mmbiz_png/laEmibHFxFw66fvRb91LzDVh1v6gXSJGtrV3XhBzTbMwArZ6tdiaUiaBRgmhLcXd1pebicWXcNia3DAiayibULicgNloAA/640?wx_fmt=png\");background-position: center center;background-repeat: no-repeat;background-attachment: initial;background-origin: initial;background-clip: initial;background-size: 50px;margin-top: 1em;margin-bottom: 10px;\"><span style=\"display: none;\"></span><span style=\"display: inline-block;height: 38px;line-height: 42px;color: rgb(72, 179, 120);background-position: left center;background-repeat: no-repeat;background-attachment: initial;background-origin: initial;background-clip: initial;background-size: 63px;margin-top: 38px;font-size: 18px;margin-bottom: 10px;\">文章参考</span></h2>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"padding-bottom: 8px;padding-top: 1em;color: rgb(74, 74, 74);line-height: 1.75em;\">springmvc的工作机制\n" +
                "            SpringMVC源码解读</p>\n" +
                "        <section data-tool=\"mdnice编辑器\" data-website=\"https://www.mdnice.com\" style=\"margin-top: -10px;padding-right: 10px;padding-left: 10px;max-width: 100%;overflow-wrap: break-word;letter-spacing: 0.034em;white-space: normal;background-color: rgb(255, 255, 255);text-align: left;font-family: Optima-Regular, Optima, PingFangSC-light, PingFangTC-light, \" PingFang SC\", Cambria, Cochin, Georgia, Times, \"Times New Roman\" , serif;line-height: 1.6;color: rgb(63, 63, 63);font-size: 16px;box-sizing: border-box !important;\">\n" +
                "            <h2 data-tool=\"mdnice编辑器\" style=\"margin-top: 1em;margin-bottom: 10px;font-weight: bold;font-size: 22px;max-width: 100%;color: black;text-align: center;background-image: url(\" https://mmbiz.qpic.cn/mmbiz_png/laEmibHFxFw4FcY3H9N5k0SDxxvvniaLdaTMeOhmoBjias3UnTnN1pskiaJ6g6IJTj7qicct1p2awPgRPwA2fbq3ILA/640?wx_fmt=png\");background-position: center center;background-repeat: no-repeat;background-attachment: initial;background-origin: initial;background-clip: initial;background-size: 50px;box-sizing: border-box !important;overflow-wrap: break-word !important;\"><span style=\"margin-top: 38px;margin-bottom: 10px;max-width: 100%;display: inline-block;height: 38px;line-height: 42px;color: rgb(72, 179, 120);background-position: left center;background-repeat: no-repeat;background-attachment: initial;background-origin: initial;background-clip: initial;background-size: 63px;font-size: 18px;box-sizing: border-box !important;overflow-wrap: break-word !important;\">最后说两句（求关注）</span></h2>\n" +
                "            <p data-tool=\"mdnice编辑器\" style=\"padding-top: 1em;padding-bottom: 8px;max-width: 100%;min-height: 1em;color: rgb(74, 74, 74);line-height: 1.75em;box-sizing: border-box !important;overflow-wrap: break-word !important;\"><strong style=\"max-width: 100%;line-height: 1.75em;box-sizing: border-box !important;overflow-wrap: break-word !important;\">最近大家应该发现微信公众号信息流改版了吧，再也不是按照时间顺序展示了。这就对阿粉这样的坚持的原创小号主，可以说非常打击，阅读量直线下降，正反馈持续减弱。</strong></p>\n" +
                "            <p data-tool=\"mdnice编辑器\" style=\"padding-top: 1em;padding-bottom: 8px;max-width: 100%;min-height: 1em;color: rgb(74, 74, 74);line-height: 1.75em;box-sizing: border-box !important;overflow-wrap: break-word !important;\">所以看完文章，哥哥姐姐们给阿粉来个<strong style=\"max-width: 100%;line-height: 1.75em;box-sizing: border-box !important;overflow-wrap: break-word !important;\">在看</strong>吧，让阿粉拥有更加大的动力，写出更好的文章，拒绝白嫖，来点正反馈呗~。</p>\n" +
                "            <p data-tool=\"mdnice编辑器\" style=\"padding-top: 1em;padding-bottom: 8px;max-width: 100%;min-height: 1em;color: rgb(74, 74, 74);line-height: 1.75em;box-sizing: border-box !important;overflow-wrap: break-word !important;\">如果想在第一时间收到阿粉的文章，不被公号的信息流影响，那么可以给Java极客技术设为一个<strong style=\"max-width: 100%;line-height: 1.75em;box-sizing: border-box !important;overflow-wrap: break-word !important;\">星标</strong>。</p>\n" +
                "            <figure data-tool=\"mdnice编辑器\" style=\"margin-top: 10px;margin-bottom: 10px;max-width: 100%;box-sizing: border-box !important;overflow-wrap: break-word !important;\"><img data-ratio=\"2.165898617511521\" data-type=\"gif\" data-w=\"434\" class=\"__bg_gif\" data-src=\"https://mmbiz.qpic.cn/mmbiz_gif/laEmibHFxFw4FcY3H9N5k0SDxxvvniaLdabb5BcJXocNAmKHbJaIl8X9GGkeP4uIYlKiaatjPaf9XUkNv4UWOsZ3w/640?wx_fmt=gif\" style=\"margin-right: auto;margin-bottom: 25px;margin-left: auto;display: block;border-radius: 4px;box-sizing: border-box !important;overflow-wrap: break-word !important;visibility: visible !important;width: 434px !important;\"></figure>\n" +
                "            <p data-tool=\"mdnice编辑器\" style=\"padding-top: 1em;padding-bottom: 8px;max-width: 100%;min-height: 1em;color: rgb(74, 74, 74);line-height: 1.75em;box-sizing: border-box !important;overflow-wrap: break-word !important;\">最后感谢各位的阅读，才疏学浅，难免存在纰漏，如果你发现错误的地方，由于本号没有留言功能，还请你在后台留言指出，我对其加以修改。</p>\n" +
                "            <p data-tool=\"mdnice编辑器\" style=\"padding-top: 1em;padding-bottom: 8px;max-width: 100%;min-height: 1em;color: rgb(74, 74, 74);line-height: 1.75em;box-sizing: border-box !important;overflow-wrap: break-word !important;\">最后谢谢大家支持~</p>\n" +
                "            <p data-tool=\"mdnice编辑器\" style=\"padding-top: 1em;padding-bottom: 8px;max-width: 100%;min-height: 1em;color: rgb(74, 74, 74);line-height: 1.75em;box-sizing: border-box !important;overflow-wrap: break-word !important;\">最最后，重要的事再说一篇~</p>\n" +
                "            <p data-tool=\"mdnice编辑器\" style=\"padding-top: 1em;padding-bottom: 8px;max-width: 100%;min-height: 1em;color: rgb(74, 74, 74);line-height: 1.75em;box-sizing: border-box !important;overflow-wrap: break-word !important;\">快来关注我呀~<br style=\"max-width: 100%;box-sizing: border-box !important;overflow-wrap: break-word !important;\">快来关注我呀~<br style=\"max-width: 100%;box-sizing: border-box !important;overflow-wrap: break-word !important;\">快来关注我呀~</p>\n" +
                "        </section>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"margin: 1em 4px;padding-top: 8px;padding-bottom: 8px;max-width: 100%;min-height: 1em;white-space: normal;background-color: rgb(255, 255, 255);font-size: 16px;text-align: center;letter-spacing: 0.75px;line-height: 26px;color: black;font-family: Optima-Regular, Optima, PingFangSC-light, PingFangTC-light, Cambria, Cochin, Georgia, Times, serif;box-sizing: border-box !important;overflow-wrap: break-word !important;\"><span style=\"max-width: 100%;color: rgb(255, 104, 39);font-size: 12px;letter-spacing: 0.544px;word-spacing: 1px;caret-color: rgb(51, 51, 51);font-family: -apple-system-font, system-ui, Arial, sans-serif;box-sizing: border-box !important;overflow-wrap: break-word !important;\">\n" +
                "                < END>\n" +
                "            </span></p>\n" +
                "        <p data-tool=\"mdnice编辑器\" style=\"margin: 1em 4px;padding-top: 8px;padding-bottom: 8px;max-width: 100%;min-height: 1em;white-space: normal;background-color: rgb(255, 255, 255);font-size: 16px;text-align: left;letter-spacing: 0.75px;line-height: 26px;color: black;font-family: Optima-Regular, Optima, PingFangSC-light, PingFangTC-light, Cambria, Cochin, Georgia, Times, serif;box-sizing: border-box !important;overflow-wrap: break-word !important;\"><span style=\"max-width: 100%;font-size: 14px;letter-spacing: 2px;word-spacing: 2px;font-family: Optima-Regular, Optima, PingFangTC-Light, PingFangSC-light, PingFangTC-light;box-sizing: border-box !important;overflow-wrap: break-word !important;\">如果大家喜欢我们的文章，欢迎大家转发，点击在看让更多的人看到。</span><span style=\"max-width: 100%;font-size: 14px;letter-spacing: 2px;word-spacing: 2px;font-family: Optima-Regular, Optima, PingFangTC-Light, PingFangSC-light, PingFangTC-light;box-sizing: border-box !important;overflow-wrap: break-word !important;\">也欢迎大家热爱技术和学习的朋友加入的我们的知识星球当中，我们共同成长，进步。</span></p>\n" +
                "        <p style=\"text-align: center;\"><img class=\"rich_pages\" data-ratio=\"0.5555555555555556\" data-s=\"300,640\" data-src=\"https://mmbiz.qpic.cn/mmbiz_png/laEmibHFxFw55nda8VgEGyCw0iaxfoUET0FPzpfL3pqfRnmwxGNPG2gBJEUWajonS5mpE9DkuoUYAmOERmiaktr2g/640?wx_fmt=png\" data-type=\"png\" data-w=\"900\" style></p>\n" +
                "    </section>\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        StringBuilder regs = new StringBuilder("\" Helvetica Neue\",|\"Hiragino Sans GB\" ,|\"Microsoft YaHei UI\" ," +
                "|\"Microsoft YaHei\" ,");
        regs.append("|\"PingFang SC\" ,|\"Times New Roman\" ,");
        html = html.replaceAll(regs.toString(),"");
        html = html.replaceAll("<p><br></p>", "");
//        createPDF("/opt/applogs/abc.pdf", html);
        try {
//            htmlToWord2("/opt/applogs/abc.doc", html);
            contentToTxt("/opt/applogs/abc.txt", html);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void htmlToWord2(String destWord, String content){
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new ByteArrayInputStream(content.getBytes("GBK"));
            os = new FileOutputStream(destWord);

            POIFSFileSystem fs = new POIFSFileSystem();
            //对应于org.apache.poi.hdf.extractor.WordDocument
            fs.createDocument(is, "WordDocument");
            fs.writeFilesystem(os);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                os.close();
                is.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    public static void contentToTxt(String destTxt, String content) {
        InputStream is = null;
        OutputStream os = null;
        try {
            content = content.replaceAll("\n                    \n\n                    \n\n                    \n " +
                    "                   \n                    ","");
            is = new ByteArrayInputStream(content.getBytes());
            os = new FileOutputStream(destTxt);

            //一次性取多少字节
            byte[] bytes = new byte[2048];
            //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
            int n = -1;
            //循环取出数据
            while ((n = is.read(bytes,0,bytes.length)) != -1) {
                //写入相关文件
                os.write(bytes, 0, n);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            try {
                os.close();
                is.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
