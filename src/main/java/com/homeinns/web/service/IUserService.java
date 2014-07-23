package com.homeinns.web.service;
import com.homeinns.web.entity.User;
import java.util.List;

public interface IUserService {
	
	public void saveUser(User user);
	public void updateUser(User user);
	public void deleteUser(String id);
	public List<User> getAllUser();
}
