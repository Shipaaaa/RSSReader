package ru.shipa.rssreader.model.network.rss;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class Channel {
    @ElementList(name = "item", inline = true)
    List<Item> itemList;
    @Element
    private String title;
    @Element
    private String link;
    @Element
    private String description;
}