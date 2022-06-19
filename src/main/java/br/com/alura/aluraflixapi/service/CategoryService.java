package br.com.alura.aluraflixapi.service;

import br.com.alura.aluraflixapi.form.CategoryForm;
import br.com.alura.aluraflixapi.model.Category;
import br.com.alura.aluraflixapi.repository.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> listAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public Category save(CategoryForm categoryForm) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryForm,category);

        categoryRepository.save(category);

        return category;
    }

    public Category update(Long id, CategoryForm categoryForm) {
        return updateFields(categoryForm,categoryRepository.getById(id));
    }

    private Category updateFields(CategoryForm categoryForm, Category category) {
        category.setTitle(categoryForm.getTitle());
        category.setColor(categoryForm.getColor());

        return category;
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.getById(id);
    }
}
