package com.github.jasondavies1.applicationpropertiesresolver.configuration;

import com.github.jasondavies1.applicationpropertiesresolver.domain.Properties;
import com.github.jasondavies1.applicationpropertiesresolver.service.MultipartFileToPropertiesConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/document/")
@RequiredArgsConstructor
public class DocumentController {

    private final MultipartFileToPropertiesConverter propertiesConverter;

    @PostMapping("upload")
    public String submit(
            @RequestParam("file") final MultipartFile file,
            final ModelMap modelMap) throws IOException {
        final Properties properties = propertiesConverter.convert(file);
        modelMap.addAttribute("propertyForm", properties);
        return "uploadedFile";
    }

    @GetMapping(value = "modified")
    @ResponseBody
    public FileSystemResource modified(
            final HttpServletResponse response,
            final Properties propertyForm) throws IOException {

        response.setHeader("Content-Disposition", "attachment; filename=app.env");

        final File temp = File.createTempFile("tmp", ".env");
        temp.deleteOnExit();

        final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(temp));
        bufferedWriter.write(propertiesToString(propertyForm));
        bufferedWriter.close();

        return new FileSystemResource(temp);
    }

    private String propertiesToString(final Properties properties) {
        return properties.getProperties().stream()
                .map(p -> {
                    final String formattedKey = p.getKey().toUpperCase()
                            .replaceAll("\\.", "_")
                            .replaceAll("-", "_");
                    return String.format("%s=%s", formattedKey, p.getValue());
                }).collect(Collectors.joining("\n"));
    }

}
