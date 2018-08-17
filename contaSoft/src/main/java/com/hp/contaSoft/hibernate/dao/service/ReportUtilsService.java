package com.hp.contaSoft.hibernate.dao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hp.contaSoft.hibernate.dao.projection.IdProjection;
import com.hp.contaSoft.hibernate.dao.repositories.PayBookDetailsRepository;
import com.hp.contaSoft.thread.CheckReportThread;
import com.hp.contaSoft.thread.CreateReportThread;

/**
 * 
 * @author williams
 * @description: service that handles the creation of pdf reports using threads 
 */

@Service
public class ReportUtilsService {
	
	@Autowired
	PayBookDetailsRepository payBookDetailsRepository;
	
	@Autowired
	FileStorageService fileStorageService;
	
	public void generateReports(Long id) {
		
		/***
		 * Get Data
		 * we get the values from a list but we need to insert the values one by one
		 * into another list because of the datasource
		 */
		Iterable<IdProjection> pbd = payBookDetailsRepository.getAllbyId(id);
		
		List<IdProjection> target = new ArrayList<>();
		pbd.forEach(target::add);
		
		ExecutorService service = null;
		List<Future<Boolean>> reportList = new ArrayList<>();
		
		try {
			service = Executors.newSingleThreadExecutor();
			//Execute the code for each element in the List
			for(IdProjection proj: target ) {
				
				System.out.println("id="+proj.getId());
				String fileName = proj.getId().toString() + proj.getRut();
				List<IdProjection> list = new ArrayList<>();
				list.add(proj);
				
				//new Thread( new CreateReportThread(fileName, id, list) ).start();
				//service.execute( new CreateReportThread(fileName, id, list) );
				//reportList.add();
				reportList.add( service.submit( new CreateReportThread(fileName, id, list)) );
				
 
			}
			
			//thread to check the responses
			service.submit(  new CheckReportThread(reportList));
		}
		
		
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
			if(service != null) service.shutdown();
		}
		
		
		
		
				
	
	}
}
