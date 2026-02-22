package com.mink.freshexpress.auth.repository;

import com.mink.freshexpress.auth.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken,String> {

    String findByKey(String username);
}
