package org.demo.dao;

import com.mongodb.*;
import org.demo.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository : User
 */
public interface UserRepository extends PagingAndSortingRepository<User, String> {

}
