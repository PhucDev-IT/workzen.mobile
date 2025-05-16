package vn.gmi.workzen.networks.models.response.auth;

import java.util.List;

public class UserResponseModel {
    String id;
    String phone;
    String email;
    String avatar;
    String fullName;
    Boolean isActive;
    List<String> roles;

}
