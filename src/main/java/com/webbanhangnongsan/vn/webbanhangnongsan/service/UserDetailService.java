package com.webbanhangnongsan.vn.webbanhangnongsan.service;

import com.webbanhangnongsan.vn.webbanhangnongsan.entity.Role;
import com.webbanhangnongsan.vn.webbanhangnongsan.entity.User;
import com.webbanhangnongsan.vn.webbanhangnongsan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Tìm người dùng theo email
        User user = userRepository.findByEmail(email);
        // Nếu không tìm thấy người dùng, ném ngoại lệ
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        // Trả về đối tượng UserDetails
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    public UserDetails loadUserByOAuth2User(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email"); // Thay đổi tùy thuộc vào provider
        User user = userRepository.findByEmail(email);

        // Nếu người dùng không tồn tại, tạo mới với vai trò USER
        if (user == null) {
            user = createNewUserFromOAuth2(oAuth2User); // Tạo người dùng mới nếu không tìm thấy
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }




    private User createNewUserFromOAuth2(OAuth2User oAuth2User) {
        User newUser = new User();
        newUser.setEmail(oAuth2User.getAttribute("email")); // Thay đổi tùy thuộc vào provider
        newUser.setPassword("N/A"); // OAuth không sử dụng mật khẩu
        newUser.setRoles(Set.of(new Role("ROLE_USER"))); // Gán vai trò USER

        // Lưu người dùng mới vào cơ sở dữ liệu
        return userRepository.save(newUser);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }
}
