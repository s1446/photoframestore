package com.photoframestore.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.photoframestore.entity.Frame;
import com.photoframestore.repository.FrameRepository;
@Service
public class FrameService {

    private final FrameRepository frameRepository;

    public FrameService(FrameRepository frameRepository) {
        this.frameRepository = frameRepository;
    }

    public void saveFrame(Frame frame) {
        frameRepository.save(frame);
    }

    
        

    public Page<Frame> getAllFrames(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return frameRepository.findAll(pageable);
    }


	public void save(Frame frame) {
		// TODO Auto-generated method stub
		 frameRepository.save(frame);
		
	}
	public void deleteById(Long id) {
	    frameRepository.deleteById(id);
	}
	  public Frame getFrameById(Long id) {
	        return frameRepository.findById(id).orElse(null);
	    }
	  public Optional<Frame> getById(Long id) {
		    return frameRepository.findById(id);
		}


}
