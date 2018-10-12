package be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.impl;

import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.ContentApi;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.model.Content;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContentApiImpl implements ContentApi {

    private ContentService contentService;

    @Autowired
    public ContentApiImpl(ContentService contentService) {
        this.contentService = contentService;
    }

    @Override
    public Content getContent_() throws Exception {
        return contentService.getContent();
    }

    @Override
    public void postContent_() throws Exception {
        contentService.postContent();
    }
}
