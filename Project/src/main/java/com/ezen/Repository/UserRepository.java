package com.ezen.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ezen.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
	
    Users findByUsername(String username);
    
    @Query("SELECT u FROM Users u WHERE u.username = :username")
    List<Users> findAllByUsername(@Param("username") String username);
    
    @Query("SELECT u.role FROM Users u WHERE u.username = :username")
    String findRoleByUsername(String username);
    
    @Query("SELECT u.password FROM Users u  WHERE u.username = :username")
    String findPasswordByUsername(String username);
    
    @Query("SELECT count(*) FROM Users u WHERE u.username = :username")
    int sameIdCheck(String username);
    
    @Query("SELECT u.id FROM Users u WHERE u.username = :username")
    int findIdByUsername(String username);
    
    @Modifying
    @Query("UPDATE Users u SET u.token = :token WHERE u.username = :username")
    void rechargeToken(String username, double token);
    
    @Query("SELECT u.token FROM Users u WHERE u.username = :username")
    double findTokenByUsername(String username);
    
    @Modifying
    @Query("UPDATE Users u SET u.password = :newPassword WHERE u.username = :username")
    void changePassword(String newPassword, String username);
    
    @Query("SELECT u.name FROM Users u WHERE u.username = :username")
    String findNameByUsername(String username);

    @Query("SELECT u.email FROM Users u WHERE u.username = :username")
    String findEmailByUsername(String username);
    
    @Query("SELECT u.phone FROM Users u WHERE u.username = :username")
    String findPhoneByUsername(String username);
    
    @Query("SELECT u.prefer_lecture FROM Users u WHERE u.username = :username")
    String findPrefer_lectureByUsername(String username);
    
    @Modifying
    @Query("UPDATE Users u SET u.name = :newName WHERE u.username = :username")
    void changeName(String newName, String username);
    
    @Modifying
    @Query("UPDATE Users u SET u.email = :newEmail WHERE u.username = :username")
    void changeEmail(String newEmail, String username);
    
    @Modifying
    @Query("UPDATE Users u SET u.phone = :newPhone WHERE u.username = :username")
    void changePhone(String newPhone, String username);
    
    @Modifying
    @Query("UPDATE Users u SET u.prefer_lecture = :newPrefer_lecture WHERE u.username = :username")
    void changePrefer_lecture(String newPrefer_lecture, String username);
    
    @Query("SELECT u FROM Users u ORDER BY id DESC")
    List<Users> findAllUserlist();
    
    @Modifying
    @Query("UPDATE Users u SET u.role = 'ROLE_LECTOR' WHERE u.id = :id")
    void changeRole(int id);
    
    @Query("SELECT u FROM Users u WHERE u.id = :user_id")
    Users findManner_scoreByUser_id(int user_id);
    
    @Query("SELECT u.reviewWriteCheck FROM Users u WHERE u.username = :username")
    String findReviewWriteCheckByUsername(String username);
    
    @Query("SELECT u FROM Users u WHERE u.id = :user_id")
    Users findUsersByUser_id(int user_id);
    
    @Query("SELECT u.all_review_score FROM Users u WHERE u.id = :user_id")
    double findAll_review_scoreByUser_id(int user_id);
    
    @Query("SELECT u.manner_score FROM Users u WHERE u.id = :user_id")
    double findManner_scoreByUserId(int user_id);
    
    @Query("SELECT u.username FROM Users u WHERE u.id = :user_id")
    String findUsernameById(int user_id);
    
    @Modifying
    @Query("UPDATE Users u SET u.all_review_score = :all_review_score WHERE u.id = :user_id")
    void updateAll_review_scoreByUser_id(double all_review_score, int user_id);
    
    @Modifying
    @Query("UPDATE Users u SET u.manner_score = :manner_score WHERE u.id = :user_id")
    void updateManner_scoreByUser_id(double manner_score, int user_id);
    
    @Modifying
    @Query("UPDATE Users u SET u.reviewWriteCheck = :reviewWriteCheck WHERE u.id = :user_id")
    void updateReviewWriteCheckByUser_id(String reviewWriteCheck, int user_id);
    
    
// 어드민 검색 기능     
    
    // id 기반 검색 기능
    @Query("SELECT u FROM Users u WHERE u.id LIKE '%' || :keyword || '%'")
    Page<Users> searchUserById(String keyword, Pageable pageable);
    
    @Query("SELECT u FROM Users u WHERE u.id LIKE '%' || :keyword || '%' AND u.role = :role")
    Page<Users> searchUserByIdAndRole(String keyword, String role, Pageable pageable);
    
    
    // username 기반 검색 기능
    @Query("SELECT u FROM Users u WHERE u.username LIKE '%' || :keyword || '%'")
    Page<Users> searchUserByUsername(String keyword, Pageable pageable);
    
    @Query("SELECT u FROM Users u WHERE u.username LIKE '%' || :keyword || '%' AND u.role = :role")
    Page<Users> searchUserByUsernameAndRole(String keyword, String role, Pageable pageable);
    
    
    // name 기반 검색 기능
    @Query("SELECT u FROM Users u WHERE u.name LIKE '%' || :keyword || '%'")
    Page<Users> searchUserByName(String keyword, Pageable pageable);
 
    @Query("SELECT u FROM Users u WHERE u.name LIKE '%' || :keyword || '%' AND u.role = :role")
    Page<Users> searchUserByNameAndRole(String keyword, String role, Pageable pageable);
    
    
    
    
    
    @Modifying
    @Query("UPDATE Users u SET u.dateApplicationForDelete = :aWeekLaterDate "
    		+ "WHERE u.username = :username")
    void dateApplicationForDeleteInsert(LocalDate aWeekLaterDate, String username);
    
    @Query("SELECT count(*) FROM Users u WHERE u.username = :username "
    		+ "AND u.dateApplicationForDelete IS NOT NULL")
    int deleteApplicationCheck(String username);
    
    @Modifying
    @Query("UPDATE Users u SET u.dateApplicationForDelete = null "
    		+ "WHERE u.username = :username")
    void deleteApplicationCancel(String username);
    
    @Modifying
    @Query("DELETE FROM Users u WHERE u.dateApplicationForDelete = :nowDate")
    void deleteUser(LocalDate nowDate);
    
    
    
    
    
    
    
    
    
    
    
    
}
