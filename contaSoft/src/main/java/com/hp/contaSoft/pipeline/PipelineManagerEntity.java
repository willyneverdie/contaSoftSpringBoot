package com.hp.contaSoft.pipeline;

import java.util.List;

import lombok.ToString;

@ToString
public class PipelineManagerEntity {
	
	private String name;
	List<PipelineChainEntity> pipelinechain;

	public List<PipelineChainEntity> getPipelinechain() {
		return pipelinechain;
	}

	public void setPipelinechain(List<PipelineChainEntity> pipelinechain) {
		this.pipelinechain = pipelinechain;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	
	

	
	
	
	
}
