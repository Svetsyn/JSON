package com.json;

import com.json.service.SeedService;
import com.json.repository.CategoryRepository;
import com.json.repository.ProductRepository;
import com.json.repository.UserRepository;
import com.json.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductService implements CommandLineRunner {

    private final SeedService seedService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(SeedService seedService, UserService userService, UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.seedService = seedService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        if(this.userRepository.count() == 0){
            seedService.seedUsers();
        }
        if(this.categoryRepository.count() == 0){
            seedService.seedCategories();
        }
        if(this.productRepository.count() == 0){
            seedService.seedProducts();
        }

        userService.giveInformationAboutUSer();

    }
}
