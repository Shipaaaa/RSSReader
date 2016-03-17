package ru.shipa.rssreader.model.network.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Vlad on 14.03.2016.
 */
@Root(name = "item", strict = false)
public class Item {
    @Element
    private String title;
    @Element
    private String description;
    @Element
    private String link;
    @Element(required = false)
    private String author;
    @Element(required = false)
    private String pubDate;
}