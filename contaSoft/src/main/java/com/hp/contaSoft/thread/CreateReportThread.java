package com.hp.contaSoft.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.core.io.ClassPathResource;

import com.hp.contaSoft.constant.HPConstant;
import com.hp.contaSoft.hibernate.dao.projection.IdProjection;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class CreateReportThread implements Callable {

	private String fileName;
	private Long id;
	private List<IdProjection> list;
	
	public CreateReportThread(String fileName, Long id, List<IdProjection> list) {
	
		this.fileName = fileName;
		this.id = id;
		this.list = list;
	}


	@Override
	public Boolean call() {

		//Add data to the datasource
		JRBeanCollectionDataSource beanColDataSource = 
				new JRBeanCollectionDataSource(list);
		
		//Load report definition
		ClassPathResource classPathResource =
				new ClassPathResource("PayCheckReport.jrxml");
		
		//init variables 
		JasperReport jasperReport = null;
        JasperPrint jasperPrint = null;
		InputStream jasperStream = null;
		Map<String,Object> params = new HashMap<>();
		File pdf;
		FileOutputStream pdfFile = null; 
		Boolean flag = false;
		
		try 
		{
			jasperStream = classPathResource.getInputStream();
			jasperReport = JasperCompileManager.compileReport(jasperStream);
			jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanColDataSource);
		
			pdf = File.createTempFile(fileName, "."+HPConstant.REPORT_EXTENSION, new File("D://filesystem"));
			System.out.println(pdf.getAbsolutePath());
			pdfFile = new FileOutputStream(pdf);
			JasperExportManager.exportReportToPdfStream(jasperPrint, pdfFile);
			pdfFile.close();
			flag = true;
			return flag;
			
			//Insert Salary Statements
			
	        			
		} 
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally 
		{
		
			jasperReport = null;
	        jasperPrint = null;
			jasperStream = null;
			
			beanColDataSource = null;
			classPathResource = null;
			
			
			
		}
		
		return flag;
		
		
	}

}
