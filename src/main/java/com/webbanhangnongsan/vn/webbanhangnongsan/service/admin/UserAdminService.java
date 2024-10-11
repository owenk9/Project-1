package com.webbanhangnongsan.vn.webbanhangnongsan.service.admin;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Product;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.User;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAdminService {
    @Autowired
    private UserRepository userRepository;
    private final static int pageSize = 1;
//    public List<User> paginatedUsers(int currentPage) {
//        int offSet = (currentPage - 1) * pageSize;
//        return userRepository.findAll()
//                .stream()
//                .skip(offSet)
//                .limit(pageSize)
//                .toList();
//    }

    public List<User> paginatedUsers(String search, int currentPage) {
        int offSet = (currentPage - 1) * pageSize;
        List<User> searchUserList = userRepository.findAll()
                .stream()
                .filter(user -> user.getName().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (search == "") {
            return userRepository.findAll()
                    .stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
        else {
            return searchUserList.stream()
                    .skip(offSet)
                    .limit(pageSize)
                    .toList();
        }
    }

//    public int totalPage() {
//        long UserQuantity = userRepository.count();
//        return (int) Math.ceil((double) UserQuantity / pageSize);
//    }

    public int totalPage(String search) {
        List<User> searchUserList = userRepository.findAll()
                .stream()
                .filter(user -> user.getName().toLowerCase().contains(search.toLowerCase()))
                .toList();
        if (search == "") {
            long userQuantity = userRepository.count();
            return (int) Math.ceil((double) userQuantity / pageSize);
        }
        else {
            int userQuantity = searchUserList.size();
            return (int) Math.ceil((double) userQuantity / pageSize);
        }
    }
}
