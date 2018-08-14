package com.hp.contaSoft.spring.controller;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSplitPaneUI;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hp.contaSoft.excel.entities.PayBookDetails;
import com.hp.contaSoft.excel.entities.Paybook;
import com.hp.contaSoft.hibernate.dao.projection.IdProjection;
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

import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvException;

import ch.qos.logback.core.net.SyslogOutputStream;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
//import net.sf.jasperreports.repo.InputStreamResource;



@Controller
public class FileControllerTest {

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
	@Autowired 
	private ApplicationContext applicationContext;
	 
	@RequestMapping("/texto")
	public ResponseEntity<String> texto() throws IOException, URISyntaxException {
		URI location = new URI("/caca");
		return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body("Hello World");
	}
	
	@RequestMapping("/imagen2")
	//@ResponseBody
	public void imagen2(HttpServletResponse response){
		
		// open image
		File imgPath = new File("C:\\Users\\williams\\Desktop\\sun.jpg");
		System.out.println(imgPath.exists());
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		
		BufferedImage bufferedImage = null;
		try {
			//bufferedImage = ImageIO.read(imgPath);
			bufferedImage = ImageIO.read(imgPath);
			ImageIO.write(bufferedImage, "jpeg", jpegOutputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] imgByte = jpegOutputStream.toByteArray();

	    response.setHeader("Cache-Control", "no-store");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);
	    response.setContentType("image/jpeg");
	    ServletOutputStream responseOutputStream;
		try {
			responseOutputStream = response.getOutputStream();
			responseOutputStream.write(imgByte);
		    responseOutputStream.flush();
		    responseOutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	@RequestMapping("/pdfJasperVoid")
	//@ResponseBody
	public void pdfJasperVoid(HttpServletResponse response){
		

		//Get Data
		Iterable<IdProjection> pbd = payBookDetailsRepository.getAll();
		List<IdProjection> target = new ArrayList<>();
		pbd.forEach(target::add);

		for(IdProjection proj: target ) {
			System.out.println(proj.getId());
		}
		
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(target);
        
		// GET jrxml file
		ClassPathResource classPathResource = new ClassPathResource("PayCheckReport.jrxml");
		System.out.println("AQUI="+classPathResource.exists());
		
		//init variables 
		JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;
		InputStream jasperStream = null;
		Map<String,Object> params = new HashMap<>();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();

		
		
		try {
			jasperStream = classPathResource.getInputStream();
			jasperReport = JasperCompileManager.compileReport(jasperStream);
			jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanColDataSource);
			//jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

			byte[] imgByte = outStream.toByteArray();

		    response.setHeader("Cache-Control", "no-store");
		    response.setHeader("Pragma", "no-cache");
		    response.setDateHeader("Expires", 0);
		    response.setContentType("application/pdf");
		    ServletOutputStream responseOutputStream;
		    
		    responseOutputStream = response.getOutputStream();
			responseOutputStream.write(imgByte);
		    responseOutputStream.flush();
		    responseOutputStream.close();
	        			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	    
	}
	
	
	@RequestMapping("/pdfJasper")
	//@ResponseBody
	public ResponseEntity<byte[]> pdfJasper() {
		
		//Datasource
		Iterable<IdProjection> pbd = payBookDetailsRepository.getAll();
		List<PayBookDetails> target = new ArrayList<>();
		//pbd.forEach(target::add);
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(target);
        
        JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;
		
		// open jrxml file
		ClassPathResource classPathResource = new ClassPathResource("PayCheckReport.jrxml");
		System.out.println("AQUI="+classPathResource.exists());
		InputStream jasperStream = null;
		Map<String,Object> params = new HashMap<>();
		File pdf = null;
		InputStream targetStream = null;
		//OutputStream outStream = null;
		OutputStream outStream = null;
		byte[] pdf1 = null;
		HttpHeaders headers = null;
		
		ByteArrayOutputStream as = new ByteArrayOutputStream();
		try {
			jasperStream = classPathResource.getInputStream();
			jasperReport = JasperCompileManager.compileReport(jasperStream);
			jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanColDataSource);
			pdf = File.createTempFile("output.", ".pdf");
			targetStream = new FileInputStream(pdf);
			outStream = new FileOutputStream(pdf);
			as.writeTo(outStream);
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			
			//pdf1 = StreamUtils.copyToByteArray(targetStream);
			pdf1 = as.toByteArray();
			
			headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF); //or what ever type it is
	        headers.setContentLength(pdf1.length);
	        			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<byte[]>(pdf1, headers, HttpStatus.ACCEPTED);
		
		
		//JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
		

		
		
/*		
		File filePath = new File("C:\\Users\\williams\\Desktop\\DynamicPDF.pdf");
		InputStream targetStream = null;
		try {
			targetStream = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(filePath.exists());
			byte[] pdf = null;
		try {
			pdf = StreamUtils.copyToByteArray(targetStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF); //or what ever type it is
        headers.setContentLength(pdf.length);
        return new ResponseEntity<byte[]>(pdf, headers, HttpStatus.ACCEPTED);
  */      
        
        
		
		 
		 

		 
		 /*
		 HttpHeaders headers = new HttpHeaders();
		 headers.setContentType(MediaType.APPLICATION_PDF);
		 List<MediaType> acceptableMediaTypes = new ArrayList<>();
		 acceptableMediaTypes.add(MediaType.APPLICATION_PDF);
		 headers.setAccept(acceptableMediaTypes);
		 URI location = new URI("/caca");
		 return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body("Hello World");
        */
        
	}
	
	
	@RequestMapping("/pdf")
	@ResponseBody
	public ResponseEntity<byte[]> pdf(){
		
		// open image
		File filePath = new File("C:\\Users\\williams\\Desktop\\DynamicPDF.pdf");
		InputStream targetStream = null;
		try {
			targetStream = new FileInputStream(filePath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(filePath.exists());
		
		

		// get DataBufferBytes from Raster
		//WritableRaster raster = bufferedImage .getRaster();
		//DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
		
		byte[] pdf = null;
		try {
			pdf = StreamUtils.copyToByteArray(targetStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF); //or what ever type it is
        headers.setContentLength(pdf.length);
        return new ResponseEntity<byte[]>(pdf, headers, HttpStatus.ACCEPTED);
	}
	
	
	@RequestMapping("/imagen3")
	@ResponseBody
	public ResponseEntity<byte[]> imagen3(){
		
		// open image
		File imgPath = new File("C:\\Users\\williams\\Desktop\\sun.jpg");
		System.out.println(imgPath.exists());
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(imgPath);
			ImageIO.write(bufferedImage, "jpeg", jpegOutputStream);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get DataBufferBytes from Raster
		//WritableRaster raster = bufferedImage .getRaster();
		//DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
		
		byte[] image = jpegOutputStream.toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); //or what ever type it is
        headers.setContentLength(image.length);
        return new ResponseEntity<byte[]>(image, headers, HttpStatus.ACCEPTED);
	}
	
	
	@RequestMapping("/imagen")
	@ResponseBody
	public HttpEntity<byte[]> imagen(){
		
		// open image
		File imgPath = new File("C:\\Users\\williams\\Desktop\\sun.jpg");
		System.out.println(imgPath.exists());
		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(imgPath);
			ImageIO.write(bufferedImage, "jpeg", jpegOutputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get DataBufferBytes from Raster
		WritableRaster raster = bufferedImage .getRaster();
		DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();
		byte[] image = jpegOutputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); //or what ever type it is
        headers.setContentLength(image.length);
        return new HttpEntity<byte[]>(image, headers);
	}
	
	
	 @RequestMapping("/report1")
	 public ResponseEntity<String> report1() throws JRException, IOException, URISyntaxException {
		 
		 
		 ClassPathResource classPathResource = new ClassPathResource("PayCheckReport.jrxml");
		 
		 System.out.println("AQUI="+classPathResource.exists());
		 InputStream jasperStream = classPathResource.getInputStream();//this.getClass().getResourceAsStream("PayCheckReport.jrxml");
		 Map<String,Object> params = new HashMap<>();
		 
		 JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);
		 //JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
		 
		 JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
		 File pdf = File.createTempFile("output.", ".pdf");

		 //final OutputStream outStream = null;//response.getOutputStream();
		 InputStream targetStream = new FileInputStream(pdf);
		 OutputStream outStream = new FileOutputStream(pdf);
		 try {
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 //  "application/x-pdf"
		 
		 //HttpHeaders responseHeaders = new HttpHeaders();
		 //responseHeaders.setContentType(MediaType.valueOf("application/pdf"));
		 HttpHeaders headers = new HttpHeaders();
		 //MultiValueMap<String, String> headers = new HttpHeaders();
		 headers.setContentType(MediaType.APPLICATION_PDF);
		 List<MediaType> acceptableMediaTypes = new ArrayList<>();
		 acceptableMediaTypes.add(MediaType.APPLICATION_PDF);
		 headers.setAccept(acceptableMediaTypes);
		 

		 //return	 new ResponseEntity<OutputStream>(outStream, headers , HttpStatus.ACCEPTED);
		 URI location = new URI("/caca");
		 return ResponseEntity.created(location).header("MyResponseHeader", "MyValue").body("Hello World");
				 //.ok().contentType(MediaType.APPLICATION_PDF).header(HttpHeaders.CONTENT_TYPE,
				 //1"inline; filename=helloWorldReport.pdf");
		 
		 
		 
		 
		 
		 
	 }
	 
	 
	 
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
