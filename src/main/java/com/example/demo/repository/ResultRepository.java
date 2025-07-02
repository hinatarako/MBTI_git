package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Result;

/**
 * Result エンティティに対する CRUD 操作が自動で提供されます。
 */
public interface ResultRepository extends JpaRepository<Result, Long> {
    // 特別なメソッドが必要な場合はここに追加。今回は基本操作で十分です。
}