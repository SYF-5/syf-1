package com.example.system.controller;

import com.example.system.common.Result;
import com.example.system.dto.UserDetailDTO;
import com.example.system.entity.User;
import com.example.system.service.UserDetailService;
import com.example.system.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserDetailService userDetailService;

    public UserController(UserService userService, UserDetailService userDetailService) {
        this.userService = userService;
        this.userDetailService = userDetailService;
    }

    @GetMapping
    public Result<List<User>> getUsers(
            @RequestParam(required = false) Integer role,
            @RequestParam(required = false) Long schoolId) {
        List<User> users;
        
        if (role != null && schoolId != null) {
            users = userService.findByRoleAndSchoolId(role, schoolId);
        } else if (role != null) {
            users = userService.findByRole(role);
        } else if (schoolId != null) {
            users = userService.findBySchoolId(schoolId);
        } else {
            users = userService.findAll();
        }
        return Result.success(users);
    }

    @GetMapping("/{id}/info")
    public Result<User> getUserInfo(@PathVariable Long id) {
        System.out.println("收到获取用户信息请求，ID: " + id);
        User user = userService.findById(id);
        return Result.success(user);
    }

    @GetMapping("/{id}")
    public Result<UserDetailDTO> getUserDetail(@PathVariable Long id) {
        UserDetailDTO userDetail = userDetailService.getUserDetail(id);
        return Result.success(userDetail);
    }

    @PutMapping("/update/{id}")
    public Result<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        System.out.println("收到更新请求，ID: " + id);
        System.out.println("用户数据: " + user.getNickname() + ", " + user.getAvatar());
        user.setId(id);
        int result = userService.update(user);
        if (result > 0) {
            return Result.success(user);
        }
        return Result.error("更新失败");
    }

    @PostMapping("/{userId}/avatar")
    public Result<String> uploadAvatar(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        try {
            if (file == null || file.isEmpty()) {
                return Result.error("文件不能为空");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return Result.error("只支持图片格式");
            }

            String originalName = file.getOriginalFilename();
            if (originalName == null || !originalName.contains(".")) {
                return Result.error("文件名无效");
            }

            String suffix = originalName.substring(originalName.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + suffix;

            String uploadDir = System.getProperty("user.dir") + "/uploads/avatars/";
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean created = dir.mkdirs();
                if (!created) {
                    return Result.error("无法创建上传目录");
                }
            }

            File dest = new File(uploadDir + newFileName);
            file.transferTo(dest);

            String imageUrl = "/images/avatars/" + newFileName;
            int result = userService.updateAvatar(userId, imageUrl);

            if (result > 0) {
                return Result.success(imageUrl);
            }
            return Result.error("头像上传失败，无法更新数据库");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("文件保存失败: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("上传失败: " + e.getMessage());
        }
    }
}
