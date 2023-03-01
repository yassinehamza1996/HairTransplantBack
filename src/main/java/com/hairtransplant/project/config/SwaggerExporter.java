package com.hairtransplant.project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
@RequestMapping("/exportSwagger")
public class SwaggerExporter {

	@Value("${swagger.baseUrl}")
	private String baseUrl;
	@Value("${server.port}")
	private String serverPort;

	@Autowired
	private ObjectMapper objectMapper;

	@GetMapping("/json")
	@ResponseBody
	public ResponseEntity<?> downloadJsonData() throws IOException {
		// Replace the URL with your own URL

		URL url = new URL(baseUrl + ":" + serverPort + "/v3/api-docs");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.connect();
		int responseCode = conn.getResponseCode();
		if (responseCode == 200) {
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder sb = new StringBuilder();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
			in.close();
			String jsonData = sb.toString();

			// Parse the JSON data and convert it to a Java object
			Object javaObject = objectMapper.readValue(jsonData, Object.class);

			// Configure the ObjectMapper to format the JSON output with indentation
			objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

			// Save the JSON data to a file
			File file = new File("Swagger.json");
			objectMapper.writeValue(file, javaObject);

			// Set the HTTP headers for the response
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Swagger.json");
			headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

			// Create an input stream for the file
			InputStream inputStream = new FileInputStream(file);
			InputStreamResource resource = new InputStreamResource(inputStream);

			// Return the file as an attachment in the HTTP response
			return ResponseEntity.ok().headers(headers).contentLength(file.length())
					.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
		}

		return ResponseEntity.badRequest().body("Failed to download JSON file : HTTP response code " + responseCode);
	}
}
