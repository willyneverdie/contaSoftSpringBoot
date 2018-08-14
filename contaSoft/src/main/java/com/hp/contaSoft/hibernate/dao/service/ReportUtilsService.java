package com.hp.contaSoft.hibernate.dao.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.hp.contaSoft.excel.entities.PayBookDetails;
import com.hp.contaSoft.hibernate.dao.projection.IdProjection;
import com.hp.contaSoft.hibernate.dao.repositories.PayBookDetailsRepository;
import com.hp.contaSoft.pipeline.Error.PipelineMessage;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 * @author williams
 * @description: service that handles the creation of pdf reports using threads 
 */

@Service
public class ReportUtilsService {
	
	@Autowired
	PayBookDetailsRepository payBookDetailsRepository;
	
	public void generateReports(Long id) {
		
		//Get Data
		List<PayBookDetails> listPBD = payBookDetailsRepository.findByPayBookInstanceId(id);
		System.out.println("Cantidad de PBI:"+listPBD.size());
		
		Iterable<IdProjection> pbd = payBookDetailsRepository.getAll();
		List<IdProjection> target = new ArrayList<>();
		pbd.forEach(target::add);

		//Execute the code for each element in the List
		for(IdProjection proj: target ) {
			
			System.out.println(proj.getId());
			List<IdProjection> list = new ArrayList<>();
			list.add(proj);
			
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
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			
			try {
				jasperStream = classPathResource.getInputStream();
				jasperReport = JasperCompileManager.compileReport(jasperStream);
				jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanColDataSource);
				//jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
				JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

				byte[] imgByte = outStream.toByteArray();

			 
			 
		        			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
				
	
	}
}
