package com.talxan.ywitter.yuser;

import com.talxan.ywitter.exceptions.SelfFollowException;
import com.talxan.ywitter.exceptions.UserNotFoundException;
import com.talxan.ywitter.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.talxan.ywitter.constants.Constant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<String> follow(Integer id) {
        User toFollow = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        User currUser = getCurrentUser();

        if (Objects.equals(toFollow.getYuserId(), currUser.getYuserId())) {
            throw new SelfFollowException("You cannot follow yourself!");
        }

        if (currUser.getFollowing().contains(toFollow)) {
            currUser.getFollowing().remove(toFollow);
            update(currUser);
            return ResponseEntity.ok().body(currUser.getFirstName() + " unfollowed " + toFollow.getFirstName());
        } else {
            currUser.getFollowing().add(toFollow);
            update(currUser);
            return ResponseEntity.ok().body(currUser.getFirstName() + " followed " + toFollow.getFirstName());
        }
    }

    @Transactional
    public List<UserResponse> getFollowing() {
        User currUser = getCurrentUser();
        return currUser.getFollowing().stream().map(UserMapper::mapToUserResponse).collect(Collectors.toList());
    }

    @Transactional
    public List<UserResponse> getFollowers() {
        User currUser = getCurrentUser();
        return userRepository.findFollowersByYuserId(currUser.getYuserId()).stream().map(UserMapper::mapToUserResponse).collect(Collectors.toList());
    }

    public String uploadPhoto(Integer id, MultipartFile file) {
        User user = getCurrentUser();
        //Integer id = user.getId();
        String photoUrl = photoFunction.apply(String.valueOf(id), file);
        user.setPhotoUrl(photoUrl);
        userRepository.save(user);
        return photoUrl;
    }

    public User update(User user) {
        if(!userRepository.existsById(user.getYuserId()))
            throw new UserNotFoundException(user.getYuserId() + " is not found.");
        userRepository.save(user);
        return user;
    }

    private final Function<String, String> fileExtension = filename -> Optional.of(filename).filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");

    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) -> {
        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)) {Files.createDirectories(fileStorageLocation);}
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(id + fileExtension.apply(image.getOriginalFilename())), REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/api/v1/image/" + id + fileExtension.apply(image.getOriginalFilename())).toUriString();
        } catch (Exception e) {
            throw new RuntimeException("Unable to save image");
        }
    };

    public User getCurrentUser() {
        return (com.talxan.ywitter.yuser.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
