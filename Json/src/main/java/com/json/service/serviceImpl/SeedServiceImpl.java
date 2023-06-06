package com.json.service.serviceImpl;

import com.google.gson.Gson;
import com.json.dtos.CategoryImportDTO;
import com.json.dtos.ProductImportDTO;
import com.json.dtos.UserImportDTO;
import com.json.entities.Category;
import com.json.entities.Product;
import com.json.entities.User;
import com.json.service.SeedService;
import com.json.repository.CategoryRepository;
import com.json.repository.ProductRepository;
import com.json.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static com.json.constant.Constant.*;

@Service
public class SeedServiceImpl implements SeedService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository,ModelMapper modelMapper, Gson gson) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }


    @Override
    public void seedUsers() throws FileNotFoundException {
        FileReader fileReader = new FileReader(USER_FILE_PATH);
        UserImportDTO[] userImportDTOS = this.gson.fromJson(fileReader, UserImportDTO[].class);


       List<User> users =  Arrays.stream(userImportDTOS)
                .map(userImportDTO -> modelMapper.map(userImportDTO,User.class)).collect(Collectors.toList());
        this.userRepository.saveAll(users);
    }

    @Override
    public void seedCategories() throws FileNotFoundException {
        FileReader fileReader = new FileReader(CATEGORIES_FILE_PATH);
        CategoryImportDTO[] categoryImportDTOS = this.gson.fromJson(fileReader,CategoryImportDTO[].class);

        List<Category> categories = Arrays.stream(categoryImportDTOS)
                .map(categoryImportDTO -> this.modelMapper.map(categoryImportDTO, Category.class))
                .collect(Collectors.toList());

            categoryRepository.saveAll(categories);

    }

    @Override
    public void seedProducts() throws FileNotFoundException {

        FileReader fileReader = new FileReader(PRODUCTS_FILE_PATH);

        ProductImportDTO[] productImportDTOS = this.gson.fromJson(fileReader,ProductImportDTO[].class);

        List<Product> products = Arrays.stream(productImportDTOS)
                .map(productImportDTO -> this.modelMapper.map(productImportDTO, Product.class))
                .collect(Collectors.toList());

//         .map(this::setRandomSeller)
//                .map(this::setRandomBayer)
        productRepository.saveAll(products);

    }

    @Override
    public void seedAll() throws FileNotFoundException {
        SeedService.super.seedAll();
    }

    private Product setRandomSeller(Product product){
        long userCount = this.productRepository.count();
        Random random = new Random();
        long randomID = random.nextInt((int) userCount) + 1;
        Optional<User> randomUser = this.userRepository.findById(randomID);

        product.setSeller(randomUser.get());

        return product;
    }

    private Product setRandomBayer(Product product){
        if (product.getPrice().compareTo(BigDecimal.valueOf(944)) >0){
            return product;
        }
        long userCount = this.productRepository.count();
        long randomID = new Random().nextInt((int) userCount)+1;
        Optional<User> randomUser = this.userRepository.findById(randomID);

        product.setBuyer(randomUser.get());
        return product;
    }
}
