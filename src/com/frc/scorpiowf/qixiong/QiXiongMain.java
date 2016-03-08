package com.frc.scorpiowf.qixiong;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.frc.scorpiowf.qixiong.bean.QiXiongConfig;
import com.frc.scorpiowf.qixiong.bean.QiXiongContext;
import com.frc.scorpiowf.qixiong.bean.QiXiongStatus;
import com.frc.scorpiowf.qixiong.processor.AbstractActionProcessor;
import com.frc.scorpiowf.qixiong.processor.ActivityProcessor;
import com.frc.scorpiowf.qixiong.processor.BuildProcessor;
import com.frc.scorpiowf.qixiong.processor.ExitProcessor;
import com.frc.scorpiowf.qixiong.processor.FarmProcessor;
import com.frc.scorpiowf.qixiong.processor.HomeProcessor;
import com.frc.scorpiowf.qixiong.processor.LogonProcessor;
import com.frc.scorpiowf.qixiong.processor.TaskProcessor;

public class QiXiongMain {
	private static String[] input_qqarr = new String[]{
		"133344251", "84378832","550702416", "344598913", "2292805430", 
		"2573944001", "2541510352", "1434827745", "1807316769", "1670765882", 
		"2821437594", "2728575188", "1498049306"};
	private static String[] input_pswarr = new String[]{"zhifan0724", "World@final11","fengrenchang", "%Bronze071111%", "fengrenchang","fengrenchang","fengrenchang","fengrenchang","fengrenchang","fengrenchang", "november", "fengrenchang", "fengrenchang"};
	private static int idx = 4;
	/*public static void main11(String[] args) {
		QiXiongMain m = new QiXiongMain();
		for (int i = 1; i < input_qqarr.length; i++) {
			idx = i;
			m.test();
		}
	}*/
	@Test
	public void test() {				
		QiXiongContext qxContext = initContext();
		LogonProcessor logonProcessor = new LogonProcessor();
		logonProcessor.setQxContext(qxContext);
		
		ExitProcessor ep = new ExitProcessor();
		ep.setQxContext(qxContext);
		
		List<AbstractActionProcessor> list = initProcessor(qxContext);
		
		System.out.println("[" + idx + "]" + "welcome " + qxContext.getQxConfig().getQqNumber());
		try {
			String result = logonProcessor.process();
			if (!"error".equals(result) && list != null) {
				for (AbstractActionProcessor pro:list) {
					result = pro.process();
					if ("error".equals(result)) {
						System.out.println("error: " + qxContext.getQxStatus().getErrorCode());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			test();
			return;
		}
		
		try {
			ep.process();
		} catch (Exception e) {
			e.printStackTrace();
			test();
			return;
		}
		
		System.out.println("final error code: " + qxContext.getQxStatus().getErrorCode());
	}
	public List initProcessor(QiXiongContext qxContext) {
		List list = new ArrayList();
		HomeProcessor hp = new HomeProcessor();
		hp.setQxContext(qxContext);
		list.add(hp);
		
		FarmProcessor fp = new FarmProcessor();
		fp.setQxContext(qxContext);
		list.add(fp);
				
		TaskProcessor tp = new TaskProcessor();
		tp.setQxContext(qxContext);
		list.add(tp);
		
		ActivityProcessor ap = new ActivityProcessor();
		ap.setQxContext(qxContext);
		list.add(ap);
		
		BuildProcessor bp = new BuildProcessor();
		bp.setQxContext(qxContext);
		list.add(bp);
		
		return list;
	}
	public QiXiongContext initContext() {
		QiXiongConfig qxConfig = new QiXiongConfig();
		qxConfig.setQqNumber(input_qqarr[idx]);
		qxConfig.setPassword(input_pswarr[idx]);
		
		QiXiongStatus qxStatus = new QiXiongStatus();
		
		QiXiongContext qxContext = new QiXiongContext();
		qxContext.setQxConfig(qxConfig);
		qxContext.setQxStatus(qxStatus);
		
		return qxContext;
	}
}
