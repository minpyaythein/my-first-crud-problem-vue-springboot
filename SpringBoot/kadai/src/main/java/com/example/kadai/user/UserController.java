package com.example.kadai.user;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.example.kadai.user.User;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository repository;

    @GetMapping
    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        return users;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    // public String createUser(@RequestParam("file") MultipartFile file,
    // @RequestParam("image") String imageString,
    // @RequestBody User user)
    public String createUser(@RequestBody User user) {
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        String imageString = user.getImage().split(",")[1];

        try {
            if (imageString != null && !imageString.isEmpty()) {
                // Decode the base64 image string
                byte[] decodedBytes = Base64.decodeBase64(imageString);

                // Generate a unique file name
                String fileName = "image_" + System.currentTimeMillis() + ".jpg";

                // Set the image path in the user object
                newUser.setImage(fileName);

                // Save the image file to the specified directory
                Path targetLocation = Paths
                        .get("/Users/minpyaythein/Desktop/Kadai/Vuejs/kadai/src/components/images", fileName);
                try (OutputStream outputStream = new FileOutputStream(targetLocation.toFile())) {
                    outputStream.write(decodedBytes);
                }
            }
            repository.save(newUser);
            return "created";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @DeleteMapping("/delete/{userId}")
    public String deleteUser(@PathVariable int userId) {
        repository.deleteById(userId);
        return "deleted";
    }

    // @PostMapping("/update/{userId}")
    // public String updateUser(@PathVariable int userId, String name, String email)
    // {
    // repository.updateUser(userId, name, email);
    // return "updated";
    // }

    @PostMapping("/update/{userId}")
    public String updateUser(@PathVariable int userId, @RequestBody User user) {
        String imageString = user.getImage().split(",")[1];
        try {
            if (imageString != null && !imageString.isEmpty()) {
                // Decode the base64 image string
                byte[] decodedBytes = Base64.decodeBase64(imageString);

                // Generate a unique file name
                String fileName = "image_" + System.currentTimeMillis() + ".jpg";

                // Set the image path in the user object
                user.setImage(fileName);

                // Save the image file to the specified directory
                Path targetLocation = Paths
                        .get("/Users/minpyaythein/Desktop/Kadai/Vuejs/kadai/src/components/images", fileName);
                try (OutputStream outputStream = new FileOutputStream(targetLocation.toFile())) {
                    outputStream.write(decodedBytes);
                }
            }
            repository.updateUser(userId, user.getName(), user.getEmail(), user.getImage());
            return "updated";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    // @PostMapping("/upload/image")
    // public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("image")
    // MultipartFile file)
    // throws IOException {

    // repository.save(User.builder()
    // .name(file.getOriginalFilename())
    // .email(file.getContentType())
    // .image(ImageUtility.compressImage(file.getBytes())).build());
    // return ResponseEntity.status(HttpStatus.OK)
    // .body(new ImageUploadResponse("Image uploaded successfully: " +
    // file.getOriginalFilename()));
    // }

    // @GetMapping(path = { "/get/image/info/{name}" })
    // public User getImageDetails(@PathVariable("name") String name) throws
    // IOException {

    // final Optional<User> dbImage = repository.findByName(name);

    // return User.builder()
    // .name(dbImage.get().getName())
    // .email(dbImage.get().getEmail())
    // .image(ImageUtility.decompressImage(dbImage.get().getImage())).build();
    // }

    // @GetMapping(path = { "/get/image/{name}" })
    // public ResponseEntity<byte[]> getImage(@PathVariable("name") String name)
    // throws IOException {

    // final Optional<User> dbImage = repository.findByName(name);

    // return ResponseEntity
    // .ok()
    // .contentType(MediaType.valueOf(dbImage.get().getEmail()))
    // .body(ImageUtility.decompressImage(dbImage.get().getImage()));
    // }

    // @GetMapping("/{userId}")
    // public ResponseEntity<User> getUser(@PathVariable int userId) {
    // Optional<User> userOptional = repository.findById(userId);
    // if (userOptional.isPresent()) {
    // User user = userOptional.get();
    // return ResponseEntity.ok(user);
    // } else {
    // return ResponseEntity.notFound().build();
    // }
    // }

    // // @PostMapping("/save")
    // // public RedirectView saveUser(User user,
    // // @RequestParam("image") MultipartFile multipartFile) throws IOException {

    // // String fileName =
    // StringUtils.cleanPath(multipartFile.getOriginalFilename());
    // // user.setPhotos(fileName);

    // // User savedUser = repository.save(user);

    // // String uploadDir = "user-photos/" + savedUser.getId();

    // // FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

    // // return new RedirectView("/users", true);
    // // }

}
