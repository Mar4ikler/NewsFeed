package app.services;

import app.dto.NewsDto;
import app.dto.NewsResponseDto;
import app.entity.Category;
import app.entity.News;
import app.repositories.CategoryRepository;
import app.repositories.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
@Service
public class NewsService implements CRUDService<NewsDto> {

    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;

    public NewsResponseDto getById1(Long id) {
        News news = newsRepository.findById(id).orElseThrow();

        NewsResponseDto newsResponseDto = new NewsResponseDto();
        newsResponseDto.setId(news.getId());
        newsResponseDto.setText(news.getText());
        newsResponseDto.setTitle(news.getTitle());
        newsResponseDto.setDate(news.getDate());
        newsResponseDto.setCategory(CategoryService.toCategoryDto(news.getCategory()));

        return newsResponseDto;
    }

    @Override
    public NewsDto getById(Long id) {
        log.info("Get news by id: {}", id);
        News news = newsRepository.findById(id).orElseThrow();
        return toNewsDto(news);
    }

    @Override
    public Collection<NewsDto> getAll() {
        log.info("Get all news");
        return newsRepository.findAll().stream()
                .map(NewsService::toNewsDto)
                .toList();
    }

    @Override
    public NewsDto create(NewsDto newsDto) {
        log.info("Create news");
        News news = toNewsEntity(newsDto);
        Long categoryId = newsDto.getCategoryId();
        Category category = categoryRepository.findById(categoryId).orElseThrow();
        news.setCategory(category);
        return toNewsDto(newsRepository.save(news));
    }

    @Override
    public NewsDto update(NewsDto newsDto) {
        log.info("Update news");
        News existingNews = newsRepository.findById(newsDto.getId()).orElseThrow();

        existingNews.setText(newsDto.getText());
        existingNews.setTitle(newsDto.getTitle());

        News updatedNews = newsRepository.save(existingNews);
        return toNewsDto(updatedNews);
    }

    @Override
    public void delete(Long id) {
        log.info("Delete news");
        newsRepository.deleteById(id);
    }

    public static NewsDto toNewsDto(News news) {
        NewsDto newsDto = new NewsDto();

        newsDto.setId(news.getId());
        newsDto.setDate(news.getDate());
        newsDto.setText(news.getText());
        newsDto.setTitle(news.getTitle());
        newsDto.setCategoryId(CategoryService.toCategoryDto(news.getCategory()).getId());

        return newsDto;
    }

    public static News toNewsEntity(NewsDto newsDto) {
        News news = new News();

        news.setText(newsDto.getText());
        news.setTitle(newsDto.getTitle());

        return news;
    }
}
