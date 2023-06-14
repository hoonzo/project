package com.ezen.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ezen.model.Lectors;

@Repository
public interface LectorsRepository extends JpaRepository<Lectors, Integer> {

}
