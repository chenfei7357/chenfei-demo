package chenfeidemo.elasticjobdemo.job;

import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Component;

@Component
public class DemoJob implements SimpleJob {
	@Override
	public void execute(ShardingContext shardingContext) {

		System.out.println("Thread:"+Thread.currentThread().getId()+" JobParameter:"+shardingContext.getShardingItem());
		System.out.println("=================================");

	}
}
