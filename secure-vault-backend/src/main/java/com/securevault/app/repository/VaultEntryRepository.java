package com.securevault.app.repository;

import com.securevault.app.entity.User;
import com.securevault.app.entity.VaultEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VaultEntryRepository extends JpaRepository<VaultEntry, Long> {

    List<VaultEntry> findAllByUser(User user);
}
