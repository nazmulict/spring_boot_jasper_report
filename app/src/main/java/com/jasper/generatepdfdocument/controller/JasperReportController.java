package com.jasper.generatepdfdocument.controller;

import com.jasper.generatepdfdocument.service.JasperReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_PDF;

@Controller
@RequestMapping("/api")
public class JasperReportController {

    static private Logger logger = LogManager.getLogger(JasperReportController.class);

    @Resource
    private JasperReportService jasperReportService;


    private HttpHeaders getHttpHeaders(String code, String lang, File invoicePdf) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(APPLICATION_PDF);
        respHeaders.setContentLength(invoicePdf.length());
        respHeaders.setContentDispositionFormData("attachment", format("%s-%s.pdf", code, lang));
        return respHeaders;
    }

    @GetMapping(value = "/generate-report", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> generateReport() throws IOException {
        logger.info("Start report generation...");
        String code = "123456";
        String lang = "en";

        final File productPdf = jasperReportService.generateProductReport(Locale.forLanguageTag(lang));
        logger.info("Report generated successfully...");

        final HttpHeaders httpHeaders = getHttpHeaders(code, lang, productPdf);
        return new ResponseEntity<>(new InputStreamResource(new FileInputStream(productPdf)), httpHeaders, OK);
    }
}
