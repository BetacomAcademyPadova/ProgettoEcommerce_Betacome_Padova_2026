package com.betacom.fe.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.betacom.fe.dto.output.ImmagineDTO;
import com.betacom.fe.dto.output.ResponseDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.services.interfaces.IUploadServices;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("rest/Upload")
public class UploadController {
	
	private final IUploadServices uplS;
	
	@PostMapping(value = "/image", consumes = "multipart/form-data")
	public ResponseEntity<ResponseDTO> uploadImage(@RequestParam("files") MultipartFile[] files,@RequestParam Integer id) throws Exception {
	    ResponseDTO r = new ResponseDTO();
	    
	    for (MultipartFile file : files) {
	        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
	            throw new AcademyException("upload_invalid");
	        }
	        uplS.saveImage(file, id);
	    }

	    r.setMsg("Upload completato");
	    return ResponseEntity.ok(r);
	}
	
	@GetMapping("/product")
	public List<ImmagineDTO> getImages(@RequestParam Integer id) {
	    return uplS.getImages(id);
	}
	@GetMapping("/getUrl")
	public ResponseEntity<ResponseDTO> getUrl(@RequestParam (required = true) String filename) 
			throws  Exception{
		ResponseDTO r = new ResponseDTO();
		r.setMsg(uplS.buildUrl(filename));
		return ResponseEntity.ok(r);
	}

}