package com.json.service.serviceImpl;

import com.google.gson.Gson;
import com.json.dtos.UserExportDTO;
import com.json.entities.User;
import com.json.repository.UserRepository;
import com.json.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.json.constant.Constant.USER_FILE_WRITER_PATH;

@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public UserServiceImp(UserRepository userRepository, ModelMapper modelMapper, Gson gson) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void giveInformationAboutUSer() throws IOException {
        System.out.println("Insert age: ");
        Scanner scanner = new Scanner(System.in);
        int age = Integer.parseInt(scanner.nextLine());
        List<User> byAgeAfter = userRepository.findByAgeAfter(age);
        String s = this.gson.toJson(byAgeAfter.stream()
                .map(e -> modelMapper.map(e, UserExportDTO.class)).collect(Collectors.toList()));

        File file = new File(s);
        file.createNewFile();
      FileWriter fileWriter = new FileWriter();

//
//
//        System.out.println(s);

    }
}
