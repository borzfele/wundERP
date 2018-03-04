package wundERP.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wundERP.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByName(String name);

}
