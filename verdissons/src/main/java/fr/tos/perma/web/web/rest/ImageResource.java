package fr.tos.perma.web.web.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;

import fr.tos.perma.web.service.FileStorageService;

@RestController
@RequestMapping("/api/images/variete")
public class ImageResource {

	@Value("${verdissons.images}")
	private String imageFolder;

	private final Path rootLocation = Paths.get("_Path_To_Save_The_File");

	private FileStorageService fileStorageService;

	public ImageResource(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}

	@GetMapping(value = "/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> getImage(@PathVariable String path) throws IOException {
		File file = new File(imageFolder + "variete/", path);
		byte[] image = new byte[0];
		try {
			image = FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok().body(image);
	}

	@PostMapping("/uploadFile")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
		String message;
		try {
			try {
				Files.copy(file.getInputStream(), this.rootLocation.resolve("file_name.pdf"));
			} catch (Exception e) {
				throw new RuntimeException("FAIL!");
			}

			message = "Successfully uploaded!";
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "Failed to upload!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

}
