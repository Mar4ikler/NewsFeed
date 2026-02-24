package app.services;

import app.dto.CategoryDto;
import app.dto.NewsDto;
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
public class CategoryService implements CRUDService<CategoryDto> {
    private final CategoryRepository repository;

    @Override
    public CategoryDto getById(Long id) {
        log.info("Get category by id: {}", id);
        Category category = repository.findById(id).orElseThrow();
        return toCategoryDto(category);
    }

    @Override
    public Collection<CategoryDto> getAll() {
        log.info("Get all categories");
        return repository.findAll().stream()
                .map(CategoryService::toCategoryDto)
                .toList();
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        log.info("Create category");
        Category category = toCategoryEntity(categoryDto);
        return toCategoryDto(repository.save(category));
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        log.info("Update category");
        Category existingCategory = repository.findById(categoryDto.getId()).orElseThrow();

        existingCategory.setTitle(categoryDto.getTitle());

        Category updatedCategory = repository.save(existingCategory);
        return toCategoryDto(updatedCategory);
    }

    @Override
    public void delete(Long id) {
        log.info("Delete category");
        repository.deleteById(id);
    }

    public static CategoryDto toCategoryDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId(category.getId());
        categoryDto.setTitle(category.getTitle());

        return categoryDto;
    }

    public static Category toCategoryEntity(CategoryDto categoryDto) {
        Category category = new Category();

        category.setTitle(categoryDto.getTitle());

        return category;
    }
}
