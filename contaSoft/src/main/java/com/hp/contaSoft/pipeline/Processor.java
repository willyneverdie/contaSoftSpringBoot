package com.hp.contaSoft.pipeline;

import com.hp.contaSoft.pipeline.Error.PipelineMessage;

public interface Processor {

	PipelineMessage run(PipelineMessage pm);
	
}
