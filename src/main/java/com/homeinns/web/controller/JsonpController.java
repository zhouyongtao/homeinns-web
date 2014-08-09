package com.homeinns.web.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * Created by Administrator on 2014/8/10.
 */
@Controller
@RequestMapping("/jsonp")
public class JsonpController {
    @RequestMapping()
    public  void index() throws IOException {
        /*
        // 直接从字符串中输入 HTML 文档
        String html = "<html><head><title>开源中国社区</title></head>"
                + "<body><p>这里是 jsoup 项目的相关文章</p></body></html>";
        Document doc = Jsoup.parse(html);

        // 从URL直接加载 HTML 文档
        Document doc = null;
        doc = Jsoup.connect("http://www.oschina.net/").get();
        String title = doc.title();

        // 从文件中加载 HTML 文档
        File input = new File("D:/test.html");
        Document doc = Jsoup.parse(input, "UTF-8", "http://www.oschina.net/");
       */
        Document doc = Jsoup.connect("http://www.oschina.net/")
                .data("query", "Java")   //请求参数
                .userAgent("I’m jsoup") //设置User-Agent
                .cookie("auth", "token") //设置cookie
                .timeout(3000)           //设置连接超时时间
                .post();                 //使用POST方法访问URL


    }
}
