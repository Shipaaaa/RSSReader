package ru.shipa.rssreader.model.network.rss;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Vlad on 14.03.2016.
 */
@Root(name = "rss", strict = false)
public class Rss {
    @Element
    private Channel channel;
    @Attribute
    private String version;

    public Channel getChannel() {
        return channel;
    }
}