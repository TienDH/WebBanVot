package com.thunga.web.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.thunga.web.entity.Author;
import com.thunga.web.entity.Book;
import com.thunga.web.entity.Category;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    // --- BEST SELLER (native, trỏ tới bảng product) ---
    @Query(value = """
        SELECT p.* 
        FROM product p 
        ORDER BY p.number_sold DESC 
        LIMIT 1
    """, nativeQuery = true)
    Book findBestSoldBook();

    // --- EXACT TITLE (derived query dùng JPQL theo field entity) ---
    List<Book> findByTitle(String title);

    // --- LIKE TITLE + COLLATION (native để dùng COLLATE) ---
    @Query(value = """
        SELECT p.* 
        FROM product p 
        WHERE p.title COLLATE utf8mb4_unicode_ci LIKE ?1
    """, nativeQuery = true)
    List<Book> findBySimilarTitle(String title, Pageable pageable);

    // --- BY AUTHOR (derived query) ---
    List<Book> findByAuthor(Author author);

    // --- LIKE AUTHOR NAME + COLLATION (native để JOIN theo bảng author) ---
    @Query(value = """
        SELECT p.*
        FROM product p
        LEFT JOIN author a ON p.author_id = a.id
        WHERE a.name COLLATE utf8mb4_unicode_ci LIKE ?1
    """, nativeQuery = true)
    List<Book> findBySimilarAuthor(String authorName, Pageable pageable);


    List<Book> findByCategory(Category category, Pageable pageable);


    @Query(value = """
        SELECT p.* FROM product p
        ORDER BY p.created_at DESC
        LIMIT ?1
    """, nativeQuery = true)
    List<Book> findNewest(int limit);

    // Lấy N sản phẩm có số lượng tồn kho > 0
    @Query(value = """
        SELECT p.* FROM product p
        WHERE p.number_in_stock > 0
        ORDER BY p.created_at DESC
        LIMIT ?1
    """, nativeQuery = true)
    List<Book> findInStockNewest(int limit);
}
