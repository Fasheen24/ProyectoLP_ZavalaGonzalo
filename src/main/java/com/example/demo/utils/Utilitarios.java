package com.example.demo.utils;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.web.multipart.MultipartFile;

public interface Utilitarios {
	public static String guardarImagen(MultipartFile foto) {
		try {
	        Path pathDire = Paths.get("src/main/resources/static/usuario_foto/");
	        if (!Files.exists(pathDire)) {
	            Files.createDirectories(pathDire);
	        }

	        // Leer la imagen
	        BufferedImage originalImage = ImageIO.read(foto.getInputStream());

	        // Redimensionar la imagen
	        int newWidth = 200; // Ancho deseado
	        int newHeight = 200; // Altura deseada
	        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g = resizedImage.createGraphics();
	        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
	        g.dispose();

	        // Guardar la imagen redimensionada
	        String filename = foto.getOriginalFilename();
	        Path path = Paths.get("src/main/resources/static/usuario_foto/" + filename);
	        ImageIO.write(resizedImage, "jpg", path.toFile());

	        return filename;
	    } catch (IOException e) {
	        System.out.println(e.getMessage());
	        return null;
	    }
	}
	
	
	
	public static String extraerHash(String passwordInput) {
		return BCrypt.hashpw(passwordInput, BCrypt.gensalt());
	}
	
	
	public static boolean checkPassword(String passwordInput, String hashPassword) {
		return BCrypt.checkpw(passwordInput, hashPassword);
	}

}
