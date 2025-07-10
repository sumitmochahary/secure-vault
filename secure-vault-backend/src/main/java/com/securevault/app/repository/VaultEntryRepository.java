package com.securevault.app.repository;

import com.securevault.app.entity.User;
import com.securevault.app.entity.VaultEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaultEntryRepository extends JpaRepository<VaultEntry, Long> {

    List<VaultEntry> findAllByUser(User user);
}
