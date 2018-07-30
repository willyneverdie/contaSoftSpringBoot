package com.hp.contaSoft.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.hp.contaSoft.constant.HPConstant;
import com.hp.contaSoft.hibernate.dao.repositories.AFPFactorsRepository;
import com.hp.contaSoft.hibernate.entities.AFPFactors;

public class PayBookUtils {

	
	@Autowired static AFPFactorsRepository afpFactorsrepository;
	
	
	public static double findPrevisionValue(String prevision) {
		
		try {
			
			System.out.println("Prevision:"+prevision);
			AFPFactors af = afpFactorsrepository.findByName(prevision);
			return af.getPercentaje();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			TimeUnit.SECONDS.sleep(HPConstant.SLEEP_TIME);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
		
	}
	
}
