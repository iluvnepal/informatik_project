////////////////////////////////////////////////////////////////////////////////
//
// Created by Kshitiz on 14.01.2021.
//
////////////////////////////////////////////////////////////////////////////////

package org.thepanday.informatikproject.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.text.StringEscapeUtils;
import org.thepanday.informatikproject.common.util.entity.Entry;
import org.thepanday.informatikproject.common.util.entity.EntryContainer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 */
public class BlaParser {

    private WebClient client;
    private String content;

    public BlaParser() {
        client = new WebClient();
        var clientOptions = client.getOptions();
        clientOptions.setJavaScriptEnabled(false);
        clientOptions.setCssEnabled(false);
        clientOptions.setUseInsecureSSL(true);
        clientOptions.setPrintContentOnFailingStatusCode(false);
    }

    public void fetchUrl(String url) throws IOException {
        HtmlPage page = client.getPage(url);
        this.content = page.asXml();
    }

    public void filterContent(Pattern pattern, int groupIndex) {
        var matcher = pattern.matcher(content);
        if (!matcher.find()) {
            throw new RuntimeException("Pattern not found!");
        }
        content = matcher.group(groupIndex);
    }

    public void decodeAscii() {
        /*
        var decoded = StandardCharsets.US_ASCII
            .decode(ByteBuffer.wrap(content.getBytes()))
            .toString();
         */

        content = StringEscapeUtils.unescapeJava(content.replaceAll("\\\\x", "\\\\u00"));
    }

    public EntryContainer toEntry() throws IOException {
        String fromFile = Files.readString(Paths.get("try_data1.json"), StandardCharsets.US_ASCII);
        var o = new ObjectMapper();
        return o.readValue(fromFile, EntryContainer.class);
    }

    public static void main(String[] args) {
        try {
            var bla = new BlaParser();
            //bla.fetchUrl("https://understat.com/league/EPL");
            //bla.filterContent(Pattern.compile("JSON.parse\\('(.*?)'\\);"), 1);
            //bla.decodeAscii();
            var result = bla.toEntry();
            System.out.println();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
