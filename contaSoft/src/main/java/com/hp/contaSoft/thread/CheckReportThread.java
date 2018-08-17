package com.hp.contaSoft.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

public class CheckReportThread implements Runnable {
	
	private List<Future<Boolean>> boolList;
	
	public CheckReportThread(List<Future<Boolean>> boolList) {
		this.boolList = boolList;
	}


	@Override
	public void run() {
		
		
		try {
			
			for(Future<Boolean> future: boolList) {
				System.out.println("future.isDone()="+future.isDone());
				System.out.println("future.get()="+future.get());
			}
			
			/*
			int listSize = list.size();
			int count = 0;
			while(true) {
				count = 0;
				for (Boolean a : list) {
					 if (a.equals(true))
						 count ++;
				}
				
				if (count == listSize) {
					System.out.println("TERMINO");
					break;
				}else {
					System.out.println("NO TERMINO");
				}
				Thread.sleep(1000);
			}
			*/
			
		}catch(Exception e) {
			
		}
		
		
		
		
		
	}

}
