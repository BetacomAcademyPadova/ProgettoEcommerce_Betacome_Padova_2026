package com.betacom.fe.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.betacom.fe.dto.output.ImmagineDTO;
import com.betacom.fe.exception.AcademyException;
import com.betacom.fe.models.Immagini;
import com.betacom.fe.models.Prodotti;
import com.betacom.fe.repositories.ICarrelloRepository;
import com.betacom.fe.repositories.IImmaginiRepository;
import com.betacom.fe.repositories.IProdottiRepository;
import com.betacom.fe.repositories.IUserRepository;
import com.betacom.fe.services.interfaces.IMessaggioServices;
import com.betacom.fe.services.interfaces.IUploadServices;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor

public class UploadImpl implements IUploadServices{
	private final IImmaginiRepository imgR;
	private final IProdottiRepository prodR;
	
	 @Value("${app.upload.dir:uploads}")
	 private String uploadDir;
	 private Path uploadPath;
	 

	 @PostConstruct
	 public void init() {
		 try {
			 uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
			 if (Files.notExists(uploadPath)) {
				 Files.createDirectories(uploadPath);
	         }

	        } catch (IOException e) {
	            throw new RuntimeException("upload_create");
	        }
	    }
	
	 @Override
	 public String saveImage(MultipartFile file, Integer id) throws Exception {
		 Assert.isTrue(!file.isEmpty(), () -> "upload_empty");
	     String uniqueName = buildFileName(file);
	     Path destinationFile = uploadPath.resolve(uniqueName);
	     try {
	    	 Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
	         Prodotti prodotto = prodR.findById(id)
	                 .orElseThrow(() -> new AcademyException("prodotto_not_found"));
	         Immagini img = new Immagini();
	         img.setNomeFile(uniqueName);
	         img.setPercorso(destinationFile.toString());
	         img.setProdotto(prodotto);
	         boolean first = imgR.findByProdottoIdProdotto(id).isEmpty();
	         img.setPrincipale(first);
	         
	         imgR.save(img);
	     } catch (IOException e) {
	         throw new AcademyException("upload_save_error");
	     }

	     return uniqueName;
	 }
	/*
	 * Normalize internal file name
	 */
	private String buildFileName(MultipartFile file) {
		String original = file.getOriginalFilename();
        String extension = "";
        String originalName = original.trim().replaceAll("\\s+", "_"); // normalize file name
 
        log.debug("originalName: {}" , originalName);
        
        extension = Optional.ofNullable(originalName)         // search extension file 
                .filter(name -> name.contains("."))
                .map(name -> name.substring(name.lastIndexOf(".")))
                .orElse("");

        // Build unique name
        return originalName.substring(0, originalName.lastIndexOf(".")) + "-" +  UUID.randomUUID().toString() + extension;

	}


	@Override
	public String buildUrl(String filename) {
		return ServletUriComponentsBuilder.fromCurrentContextPath()  // recupera la parte iniziale dell URL // localhost:8080/
                .path("/images/")    // il prefisse sarebbe image
                .path(filename)                 // il nome del file
                .toUriString();
	}
	
	public List<ImmagineDTO> getImages(Integer idProdotto) {
	    return imgR.findByProdottoIdProdotto(idProdotto)
	            .stream()
	            .map(i -> ImmagineDTO.builder()
	                    .id(i.getId())
	                    .nomeFile(i.getNomeFile())
	                    .url(buildUrl(i.getNomeFile()))
	                    .principale(i.getPrincipale())
	                    .build())
	            .toList();
	}

}