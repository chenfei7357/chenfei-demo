package chenfeidemo.elasticjobdemo.job;

import com.google.common.collect.Lists;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoDataFlowJob implements DataflowJob<String> {

	@Override
	public List<String> fetchData(ShardingContext shardingContext) {
		return Lists.newArrayList("1");
	}

	@Override
	public void processData(ShardingContext shardingContext, List<String> list) {

		System.out.println("Thread:+"+Thread.currentThread().getId()+" JobParameter:"+shardingContext.getShardingItem()+" " +
				"list:"+list);
	}
}
