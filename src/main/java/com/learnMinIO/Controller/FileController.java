package com.learnMinIO.Controller;

import com.learnMinIO.Service.MinIoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FileController {

    @Autowired
    private MinIoService service;

    @PostMapping("/upload")
    public ResponseEntity<Map<String,String>>upload(@RequestParam("file")MultipartFile file){
        try{
            String fileName= service.uploadFile(file);
            String url=service.getFile(fileName);
            Map<String,String>response=new HashMap<>();
            response.put("fileName",fileName);
            response.put("url",url);

            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/get/{filename}")
    public ResponseEntity<String> getFile(@PathVariable String filename){
        try{
            String url=service.getFile(filename);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{filename}")
    public ResponseEntity<String> deleteFile(@PathVariable String filename){
        try {
            service.deleteFile(filename);
            return ResponseEntity.ok("delete file successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
