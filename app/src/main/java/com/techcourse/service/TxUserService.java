package com.techcourse.service;

import com.techcourse.config.DataSourceConfig;
import com.techcourse.domain.User;
import org.springframework.transaction.support.TransactionManager;

public class TxUserService implements UserService {

    private final UserService userService;
    private final TransactionManager transactionManager;

    public TxUserService(final UserService userService) {
        this.userService = userService;
        this.transactionManager = new TransactionManager(DataSourceConfig.getInstance());
    }

    @Override
    public User findById(long id) {
        return transactionManager.execute(() -> userService.findById(id));
    }

    @Override
    public User findByAccount(String account) {
        return transactionManager.execute(() -> userService.findByAccount(account));
    }

    @Override
    public void insert(User user) {
        transactionManager.execute(() -> {
            userService.insert(user);
            return null;
        });
    }

    @Override
    public void changePassword(long id, String newPassword, String createBy) {
        transactionManager.execute(() -> {
            userService.changePassword(id, newPassword, createBy);
            return null;
        });
    }
}
