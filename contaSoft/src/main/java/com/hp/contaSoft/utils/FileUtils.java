package com.hp.contaSoft.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hp.contaSoft.constant.HPConstant;
import com.hp.contaSoft.excel.entities.PayBookDetails;
import com.hp.contaSoft.hibernate.dao.repositories.AFPFactorsRepository;
import com.hp.contaSoft.hibernate.dao.repositories.AFPFactorsRepositoryImpl;
import com.hp.contaSoft.hibernate.entities.AFPFactors;
import com.hp.contaSoft.hibernate.entities.PayBookInstance;
import com.hp.contaSoft.hibernate.entities.Taxpayer;
import com.hp.contaSoft.initial.PostConstructBean;
import com.hp.contaSoft.pipeline.Error.PipelineMessage;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

public class FileUtils {



	
	
	public static PipelineMessage processCSVFile(PipelineMessage pmInput) {
		
		try {
			
			PipelineMessage pmOutput = pmInput;
			
			System.out.println("Method:processCSVFile- i!=0");
			System.out.println(pmOutput);
			TimeUnit.SECONDS.sleep(HPConstant.SLEEP_TIME);
			
			TaxpayerRepositoryImpl taxpayerRepository = new TaxpayerRepositoryImpl();
			
			/*parse CSV file*/
			Reader reader = new InputStreamReader(pmOutput.getIsInput2());
			CsvToBean<PayBookDetails> cb = new CsvToBean<>();
	        HeaderColumnNameMappingStrategy<PayBookDetails> hc = new HeaderColumnNameMappingStrategy<PayBookDetails>();
	        hc.setType(PayBookDetails.class);
	    
	        cb = new CsvToBeanBuilder<PayBookDetails>(reader)
	          .withType(PayBookDetails.class)
	          .withMappingStrategy(hc)
	          .withIgnoreLeadingWhiteSpace(true)
	          .withSeparator(';')
	          .withThrowExceptions(false) 
	          .build();
	    
	        //List of PayBookDetails - Entity with file data
	        List<PayBookDetails> detailsList = cb.parse();
	        
			System.out.println("DetailList="+detailsList );

	        //Repository for PayBookDetails
	        PayBookDetailsRepositoryImpl payBookDetailsRepository = new PayBookDetailsRepositoryImpl();
	        
	        //Get List of PayBookDetails from PayBookInstance
	        PayBookInstance pbi = pmInput.getPayBookInstance();
	    	List<PayBookDetails> pbd = pbi.getPayBookDetails();
	        
	    	
	    	//Loop List of PayBookDetails
	        for(PayBookDetails detail : detailsList)
	        {
	        	/*****
            	 * 5.Calculate Fields
            	 * @author williams
            	 * @description: calculate fields. Maybe we can use Threads.
            	 */

	        	/**	        	 
	        	 * static values
	        	 */
	        	AFPFactorsRepository afpFactorsRepository = new AFPFactorsRepositoryImpl(); 
	        	AFPFactors afp = afpFactorsRepository.findByName(detail.getPrevision());
	    		System.out.println("afp="+afp);
	    		TimeUnit.SECONDS.sleep(HPConstant.SLEEP_TIME);
	        	if(afp!= null)
	    			detail.setPorcentajePrevision(afp.getPercentaje());
	        	
	        	/**
	        	 * Valores Imponibles
	        	 */
	        	detail.calculateSueldoMensual();
	        	detail.calculateGratificacion();
	        	detail.calculateValorHora();
	        	detail.calculateTotalImponible();
	        	
	        	
	        	detail.calculateAfc();
	        	detail.calculatePrevision();
	        	detail.calculateSalud();
	        	detail.calculateTotalHaber();
	        	
	        	detail.calculateRentaLiquidaImponible();
	        	detail.calculateSeguroAccidentes();
	        	
	        	
	        	detail.calculateValorUIT();
	        	
	        	//Add PayBookDetail to PayBookInstance
	        	pbd.add(detail);
	        	
	        	//Persist PayBookDetail
	        	payBookDetailsRepository.save(detail);
	        	
	        	//Detach PayBookDetail
		        //payBookDetailsRepository.detach(detail);
	        }
	        
	        
	        //Persist TaxPayer
	        Taxpayer tax = pmInput.getTaxpayerInput();
	         
	        
	        int i = tax.getPayBookInstance().indexOf(pbi);
	        
	        tax.getPayBookInstance().get(i).setPayBookDetails(pbd);
	        taxpayerRepository.save2(tax);
	        
	        //Detach TaxPayer
	        //taxpayerRepository.detach(tax);
	        
	        //List PayBookDetail
	        System.out.println("Se lista objeto PaybookDetail");
	    	payBookDetailsRepository.listAll();
	    	
	    	//TimeUnit.SECONDS.sleep(30);
	    	
	        return pmOutput;
	        
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return pmInput;
		
		
	
        
        
        

	}

	
	public static PipelineMessage insertPayBookInstance(PipelineMessage pmInput) {
			
		try {

			System.out.println("Method insertPayBookInstance ");
			System.out.println("pmInput:"+pmInput);
			TimeUnit.SECONDS.sleep(HPConstant.SLEEP_TIME);
			
			
			TaxpayerRepositoryImpl taxpayerRepository = new TaxpayerRepositoryImpl();
			Taxpayer tax = pmInput.getTaxpayerInput();

			PayBookInstanceRepositoryImpl payBookInstanceRepositoryImpl = new PayBookInstanceRepositoryImpl();
            PayBookInstance pbi = new PayBookInstance(pmInput.getFileMonthOutput(), "Test Upload", pmInput.getFileNameInput(), pmInput.getFileClientRutOutput());
			
			
			//Calculate VERSION of the Instance
			Lock lock = new ReentrantLock();
			lock.lock();
			try {				
				long version = payBookInstanceRepositoryImpl.getVersionByRut(pmInput.getFileClientRutOutput(),pmInput.getFileMonthOutput());
				pbi.setVersion((int) version);
			}catch(Exception e)
			{
				pmInput.setValid(false);
				e.printStackTrace();
			}
			finally {
				lock.unlock();
			}

			 
			
			
			
            
            //save PayBookInstance
            tax.getPayBookInstance().add(pbi);
            taxpayerRepository.save(tax);
            
            //list PayBookInstance
            System.out.println("Lista PayBookInstance");
            payBookInstanceRepositoryImpl.listAll();

            pmInput.setPayBookInstance(pbi);
            
            return pmInput;
		}
		catch(Exception e) {
			pmInput.setValid(false);
			e.printStackTrace();
		}
		
		return pmInput;

	}
	
	public static boolean validateMonth(String monthName) throws InterruptedException {
		
		
		System.out.println("Method validateMonth ");
		System.out.println("Month:"+monthName);
		TimeUnit.SECONDS.sleep(HPConstant.SLEEP_TIME);
		
		Locale spanish = new Locale("es", "ES");
		Date date;
		
		try {
			date = new SimpleDateFormat("MMMM", spanish).parse(monthName);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			System.out.println(cal.get(Calendar.MONTH));
			return true;
		
		} catch (ParseException e) {
			
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static Taxpayer validateTaxpayerByRut(String rut) throws InterruptedException {
		
		System.out.println("Method validateTaxpayerByRut ");
		System.out.println("Rut:"+rut);
		TimeUnit.SECONDS.sleep(HPConstant.SLEEP_TIME);
		
		TaxpayerRepositoryImpl taxpayerRepository = new TaxpayerRepositoryImpl();
        Taxpayer tax = taxpayerRepository.getTaxpayerByRut(rut);
		
        System.out.println("Taxpayer:"+tax);
		TimeUnit.SECONDS.sleep(HPConstant.SLEEP_TIME);
        
        if (tax != null)
        	return tax;
        else
        	return null;
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public static PipelineMessage validateFileName(PipelineMessage pmInput) {
		
		String fileName = pmInput.getFileNameInput();
		PipelineMessage pmOutput = pmInput;
		
		
		try {
			
			System.out.println("FileName:"+fileName);
			
			/**
			 *Validate CSV Extension
			 */
			String fileNames[] = fileName.split("\\.");
			String extension = fileNames[1];
			System.out.println("extension:"+extension);
			if (!extension.equalsIgnoreCase(HPConstant.FILE_EXTENSION)) {
				pmOutput.setValid(false);
				//pm.setErrorMessage(getErrorMessage(01));
				return pmOutput;
			}
			
			/**
			 *Get RUT & MONTH
			 */
			String fileNam[] = fileNames[0].split("_");
	        System.out.println("RUT:"+fileNam[0]+ " Mes:"+fileNam[1]);
	        String rut = fileNam[0];
	        String month = fileNam[1];
	        pmOutput.setFileMonthOutput(month);
	        pmOutput.setFileClientRutOutput(rut);
	        
	        
			/**
			 *Validate RUT
			 */
	        Taxpayer tax = validateTaxpayerByRut(rut);
			if( tax == null) {
				pmOutput.setValid(false);
				//pm.setErrorMessage(getErrorMessage(02));
				return pmOutput;
			}
			pmOutput.setTaxpayerInput(tax);
			
	 		/**
			 *Validate MONTH
			 */
			System.out.println("Method Validate MONTH");
			System.out.println("Month:"+month);
			TimeUnit.SECONDS.sleep(HPConstant.SLEEP_TIME);
			 
			
			if(!validateMonth(month)) {
				pmOutput.setValid(false);
				//pm.setErrorMessage(getErrorMessage(03));
				return pmOutput;
			}
			
			
		}catch(Exception e) {
			System.err.println(e.getMessage());
			pmOutput.setValid(false);
			return pmOutput;
		}
		
		finally {
			
			
		}
        
		return pmOutput;
        
        
	}
	
	public static PipelineMessage validateHeaders(PipelineMessage pmInput) {
		
		PipelineMessage pmOutput = pmInput;
		
        try {
        	boolean validheader = false;
        	boolean validA = false;
        	boolean validB = false;
        	boolean validC = false;
        	InputStream is = pmOutput.getIsInput();
        	
        	
        	Reader reader = new InputStreamReader(is);
            CSVReader csvReader = new CSVReader(reader);
			String[] nextRecord = csvReader.readNext();
			
			System.out.println("Cabecera:"+nextRecord[0]);
			String fileHeaders = nextRecord[0];
			
			//List<String> fileHeaders = Arrays.asList(nextRecord[0]);
			//fileHeaders.forEach(fileHeader -> System.out.println("Cabecera2;"+fileHeader));
			
			/**
			 * Obtain Map with the templates 
			 */
			
			Map<String, String> template = PostConstructBean.taxpayerTemplates.get("15961703-3");
			String templateHeaders =  template.get("RENTA");
			
			/**
			 * 2. compare fileHeaders and templateHeaders
			           two ways to compare: a.- string vs string directly; lenght (equals doesnt work cause or the columns order)			
									        b.- parse strings and compare every value
			           						c.- validate required headers
					If there is a mistmatch i must return which header we are missing
			 */
			
			//a
			
			System.out.println("Largo FILE:"+ fileHeaders.length() + " Largo template"+templateHeaders.length());
			if(fileHeaders.length() != templateHeaders.length())
				{
					pmOutput.setValid(false);
					//pm.setErrorMessage(getErrorMessage(01));
					return pmOutput;
				}
				
			//b to be implement!
			
			String[] fileArrayHeaders = fileHeaders.split(";");
			String[] templateArrayHeaders = templateHeaders.split(";");
			
			int fileLenght = fileArrayHeaders.length;
			
			reader.close();
			csvReader.close();
			return pmOutput;
			
		} catch (IOException e) {
			e.printStackTrace();
			pmOutput.setValid(false);
		}
        
		return pmOutput;
		
	}
	
	
}
