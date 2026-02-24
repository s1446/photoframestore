package com.photoframestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.photoframestore.entity.Frame;

public interface FrameRepository extends JpaRepository<Frame, Long> {
}
