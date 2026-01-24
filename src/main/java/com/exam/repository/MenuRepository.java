package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.exam.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
