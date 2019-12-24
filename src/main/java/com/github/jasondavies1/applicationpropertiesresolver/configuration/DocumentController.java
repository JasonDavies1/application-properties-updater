package com.github.jasondavies1.applicationpropertiesresolver.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/document/")
public class DocumentController {

    @PostMapping("upload")
    public String submit(
            @RequestParam("file") final MultipartFile file,
            final ModelMap modelMap) throws IOException {
        final String fileContents = new String(file.getInputStream().readAllBytes());
        modelMap.addAttribute("fileContents", fileContents);
        return "uploadedFile";
    }

}
