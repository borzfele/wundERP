package wundERP.services;

import wundERP.models.User;

public interface UserService {

    User findByName(String name);

}
