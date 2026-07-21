package com.betacom.fe.services.interfaces;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.betacom.fe.dto.output.ImmagineDTO;

public interface IUploadServices {
	String saveImage(MultipartFile file, Integer idProdotto) throws Exception;
	String buildUrl(String filename);
	List<ImmagineDTO> getImages(Integer id);
}
