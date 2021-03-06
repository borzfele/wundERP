package wundERP.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import wundERP.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
