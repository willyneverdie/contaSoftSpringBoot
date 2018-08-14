package com.hp.contaSoft.pipeline.chain.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.hp.contaSoft.hibernate.dao.service.FileUtilsService;
import com.hp.contaSoft.pipeline.Processor;
import com.hp.contaSoft.pipeline.Error.PipelineMessage;


@Component
public class ValidateFileNameProcessor implements Processor {

	private PipelineMessage pmOutput;
	@Autowired
	private FileUtilsService fileUtilsService;
	
	
	@Override
	public PipelineMessage run(PipelineMessage pmInput) {
		// 
		
		try 
		{
 			return pmOutput = fileUtilsService.validateFileName(pmInput);
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("post-run");
			pmInput.setValid(false);
			pmInput.setErrorMessageOutput("Exception ValidateFileNameProcessor");
		}
		
		
		
		return pmInput;
	}

}
