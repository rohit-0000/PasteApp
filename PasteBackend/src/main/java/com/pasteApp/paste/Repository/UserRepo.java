package com.pasteApp.paste.Repository;

import com.pasteApp.paste.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, ObjectId> {
    public User findByUserName(String userName);
    void deleteByUserName(String userName);
    public User findByEmail(String email);
}
