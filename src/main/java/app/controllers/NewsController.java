package app.controllers;

import app.dto.NewsDto;
import app.dto.NewsResponseDto;
import app.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseDto> getNewsById(@PathVariable("id") Long id) {
        NewsResponseDto newsDto = newsService.getById1(id);
        if (newsDto != null) {
            return ResponseEntity.ok(newsDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<Collection<NewsDto>> getAllNews() {
        Collection<NewsDto> newsDtos = newsService.getAll();
        return ResponseEntity.ok(newsDtos);
    }

    @PostMapping
    public ResponseEntity<NewsDto> createNews(@RequestBody NewsDto newsDto, UriComponentsBuilder uriBuilder) {
        NewsDto createdNews = newsService.create(newsDto);

        URI location = uriBuilder
                .path("/api/news/{id}")
                .buildAndExpand(createdNews.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdNews);
    }

    @PutMapping
    public ResponseEntity<NewsDto> updateNews(@RequestBody NewsDto newsDto) {
        NewsDto updatedNewsDto = newsService.update(newsDto);
        return ResponseEntity.ok(updatedNewsDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable("id") Long id) {
        newsService.delete(id);
        return ResponseEntity.noContent().build();
//        if (deletedNews != null) {
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
    }

}
