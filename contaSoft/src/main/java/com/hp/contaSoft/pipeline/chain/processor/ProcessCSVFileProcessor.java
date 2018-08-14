package com.hp.contaSoft.pipeline.chain.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hp.contaSoft.hibernate.dao.service.FileUtilsService;
import com.hp.contaSoft.pipeline.Processor;
import com.hp.contaSoft.pipeline.Error.PipelineMessage;


@Component
public class ProcessCSVFileProcessor implements Processor {

	private PipelineMessage pm; 
	@Autowired
	private FileUtilsService fileUtilsService;
	
	@Override
	public PipelineMessage run(PipelineMessage pmInput) {
		// TODO Auto-generated method stub
		
		try 
		{
			//return pm = FileUtils.processCSVFile(pmInput);
			return pm = fileUtilsService.processCSVFile(pmInput);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		return null;
	}

}
