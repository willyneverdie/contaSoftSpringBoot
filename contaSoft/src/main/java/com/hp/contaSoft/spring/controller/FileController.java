package com.hp.contaSoft.spring.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.synth.SynthSplitPaneUI;

import org.apache.commons.collections4.IteratorUtils;
//import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hp.contaSoft.excel.entities.PayBookDetails;
import com.hp.contaSoft.excel.entities.Paybook;
import com.hp.contaSoft.hibernate.dao.projection.PrevisionProjection;
import com.hp.contaSoft.hibernate.dao.repositories.AFPFactorsRepository;
import com.hp.contaSoft.hibernate.dao.repositories.AddressRepository;
import com.hp.contaSoft.hibernate.dao.repositories.IUTRepository;
import com.hp.contaSoft.hibernate.dao.repositories.PayBookDetailsRepository;
import com.hp.contaSoft.hibernate.dao.repositories.PayBookInstanceRepository;
import com.hp.contaSoft.hibernate.dao.repositories.TaxpayerRepository;
import com.hp.contaSoft.hibernate.dao.service.FileStorageService;
import com.hp.contaSoft.hibernate.dao.service.FileUtilsService;
import com.hp.contaSoft.hibernate.entities.AFPFactors;
import com.hp.contaSoft.hibernate.entities.Address;
import com.hp.contaSoft.hibernate.entities.IUT;
import com.hp.contaSoft.hibernate.entities.PayBookInstance;
import com.hp.contaSoft.hibernate.entities.Subsidiary;
import com.hp.contaSoft.hibernate.entities.Taxpayer;
import com.hp.contaSoft.hibernate.entities.Template;
import com.hp.contaSoft.pipeline.PipelineManager;
import com.hp.contaSoft.pipeline.Error.PipelineMessage;
import com.hp.contaSoft.utils.FileUtils;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvException;

import ch.qos.logback.core.net.SyslogOutputStream;



@Controller
public class FileController {

	private String saveDirectory = "D:/";
	
	@Autowired
	PipelineManager pm;
	@Autowired
	PipelineMessage pipelineMessageInput, pipelineMessageOutput;
	@Autowired 
	AFPFactorsRepository afpFactorsrepository;	
	@Autowired
	TaxpayerRepository taxpayerRepository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	private FileUtilsService fileUtilsService;
	@Autowired
	private IUTRepository iUTRepository;
	@Autowired
	private PayBookInstanceRepository payBookInstanceRepository;
	@Autowired
	private PayBookDetailsRepository payBookDetailsRepository;
	
	
	 @Autowired
	 private FileStorageService fileStorageService;
	
	@RequestMapping("/uploadFile")
	public String handleCharge(HttpServletRequest request, Model model) {
		
		
		//List PayBookInstance
		//List<PayBookInstance> listPBI = (List<PayBookInstance>) payBookInstanceRepository.findAllByTaxpayerId(id);
			//List<PayBookInstance> listPBI = (List<PayBookInstance>) payBookInstanceRepository.findAllByTaxpayerIdOrderByVersionDesc(id);
	//	System.out.println("Cantidad de PBI:"+listPBI.size());
		
		
		//model.addAttribute("pbi", listPBI);
		
		return "uploadFile";
	}
	
	@RequestMapping("/processFile")
	public String handleProcessFile(@RequestParam("file") MultipartFile file) {
		
		String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("D://")
                .path(fileName)
                .toUriString();

        //return new UploadFileResponse(fileName, fileDownloadUri,
          //      file.getContentType(), file.getSize());
		
		return "uploadFile";
	}
	
	
}
