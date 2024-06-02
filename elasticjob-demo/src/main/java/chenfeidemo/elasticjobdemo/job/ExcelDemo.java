package chenfeidemo.elasticjobdemo.job;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.StopWatch;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

public class ExcelDemo {

	public static void main(String[] args) throws Exception {
		// 文件输出位置
		StopWatch stopWatch = new StopWatch("大数据批量导出");
		stopWatch.start("创建文件");
		OutputStream out = new FileOutputStream("/Users/yh/Desktop/demo.xlsx");
		ExcelWriter writer = EasyExcelFactory.write(out).autoCloseStream(true).build();
		// 动态添加表头，适用一些表头动态变化的场景
		WriteSheet sheet = new WriteSheet();
		sheet.setSheetName("非注解导出");
		sheet.setSheetNo(0);
		// 创建一个表格，用于 Sheet 中使用
		WriteTable table = new WriteTable( );
		table.setTableNo(1);
		table.setHead(head(fieldHead()));
		stopWatch.stop();
		//分批次写数据
		List<List<Object>> result=Lists.newArrayList();
		stopWatch.start("获取导出的数据");
		for (int i = 0; i <=50 ; i++) {
//			writer.write(contentData(fieldHead(), data(i)), sheet, table);
			result.addAll(contentData(fieldHead(), data(i)));
		}
		writer.write(result, sheet, table);
		writer.finish();
		stopWatch.stop();
		out.close();
		System.out.println(stopWatch.prettyPrint());
		Thread.sleep(10000000);
	}

	private static List<List<Object>> contentData(List<Map<String,String>> fieldHead,
	List<Map<String,Object>> data) {
		/**
		 * 这里一个List<Object>才代表一行数据，
		 * 需要映射成每行数据填充，横向填充（把实体数据的字段设置成一个List<Object>）
		 */
		List<List<Object>> res =Lists.newArrayList();
		//根据对应的头字段名匹配按顺序匹配对应的值，每行数据组装成一个List<Object>
		Map<String, String> fieldHeadMap = fieldHead.get(0);
		for (Map<String, Object> datum : data) {
			List<Object> object = Lists.newArrayList();
			Iterator<String> fieldHeadIterator = fieldHeadMap.keySet().iterator();
			while (fieldHeadIterator.hasNext()){
				object.add(datum.get(fieldHeadIterator.next()));
			}
			res.add(object);
		};
		return res;
	}

	private static List<List<String>> head(List<Map<String,String>> fieldHead) {
		List<List<String>> head = Lists.newArrayList();
		Map<String, String> stringStringMap = fieldHead.get(0);
		for (String value : stringStringMap.values()) {
			head.add(Lists.newArrayList(value));
		}
		return head;
	}



	private static List<Map<String,String>> fieldHead(){
		List<Map<String,String>> fieldHeadList =Lists.newArrayList();

		Map<String,String> a=Maps.newHashMap();
		//key为字段名 value为列名的中文描述
		a.put("a","第一列");
		//key为字段名 value为中文描述
		a.put("b","第二列");
		//key为字段名 value为中文描述
		a.put("c","第三列");
		//key为字段名 value为中文描述
		a.put("d","第四列");
		fieldHeadList.add(a);
		return fieldHeadList;
	}


	private static List<Map<String,Object>> data(int j){
		List<Map<String,Object>> datas =Lists.newArrayList();
			for (int i = 0; i <10000 ; i++) {
				Map<String,Object> a=Maps.newHashMap();
				//key为字段名 value为列名对应的值
				a.put("a","第一列值:"+"j:"+j+" i:"+i);
				//key为字段名 value为中文描述
				a.put("b","第二列值:"+"j:"+j+" i:"+i);
				//key为字段名 value为中文描述
				a.put("c","第三列值:"+"j:"+j+" i:"+i);
				//key为字段名 value为中文描述
				a.put("d","第四列值:"+"j:"+j+" i:"+i);
				datas.add(a);
			}
		return datas;

	}
	/**
	 *
	 任务表：

	 id、
	 所属模块的id（导出配置表头id）
	 导出的请求参数（json）
	 导出请求人（工号）
	 导出任务进度、
	 导出任务状态、
	 导出文件地址、








	 导出配置表头：

	 id
	 所属中心编码、
	 所属中心描述、
	 所属模块编码、
	 所属中心描述、
	 所属中心域名、
	 所属模块回调查总条数的接口地址、
	 所属模块回调查总条数的接口请求方式（默认POST请求）、
	 所属模块回调查分页查下接口地址、
	 所属模块回调查分页查下接口请求方式（默认POST请求）、


	 导出配置具体字段配置表：

	 id、
	 表头关联id、
	 字段名、
	 导出字段中文描述、
	 导出字段排序号、
	 */
}
