package be.bosa.%PROJECT%.%SERVICE-LONG-1W%.service.impl;

import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.api.model.Content;
import be.bosa.%PROJECT%.%SERVICE-LONG-1W%.service.ContentService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!mockdata")
public class ContentServiceImpl implements ContentService {
    @Override
    public Content getContent() {
        return null;
    }

    @Override
    public void postContent() {

    }
}
