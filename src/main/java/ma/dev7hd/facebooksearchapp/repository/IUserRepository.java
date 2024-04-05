package ma.dev7hd.facebooksearchapp.repository;

import ma.dev7hd.facebooksearchapp.entities.FBUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface IUserRepository extends JpaRepository<FBUser,Long> {
    FBUser findByPhone(Long phone);
    FBUser findByEmail(String email);

    Slice<FBUser> findAllByLastNameStartingWith(@Param("prefix") String lastName, Pageable pageable);
    Slice<FBUser> findAllByFirstNameStartingWithAndLastNameStartingWith(@Param("fName") String firstName, @Param("lName") String lastName, Pageable pageable);

    int countByLastNameStartingWith(@Param("prefix") String lastName);
    int countByFirstNameStartingWithAndLastNameStartingWith(@Param("fName") String firstName, @Param("lName") String lastName);

}
