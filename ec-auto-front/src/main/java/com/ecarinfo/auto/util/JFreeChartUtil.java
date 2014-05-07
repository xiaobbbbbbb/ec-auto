package com.ecarinfo.auto.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;

import com.ecarinfo.common.utils.DateUtils;
import com.ecarinfo.common.utils.DateUtils.TimeFormatter;

/**
 * Hello world!
 * 
 */
public class JFreeChartUtil {
	/**
	 * 柱子颜色
	 */
	private static final Color BAR_COLOR = new Color(70, 124, 185);
	
	public static void main(String[] args) {
		List<KeyVal> list1 = new ArrayList<KeyVal>();
		list1.add(new KeyVal("外观",1718d));  
		list1.add(new KeyVal("内饰",218d));  
		list1.add(new KeyVal("空间",2018d));  
		list1.add(new KeyVal("操控",418d));  
		list1.add(new KeyVal("动力",138d)); 
		list1.add(new KeyVal("油耗",156d)); 
		list1.add(new KeyVal("舒适性",279d)); 
		list1.add(new KeyVal("配置",47d));
		list1.add(new KeyVal("价格",31d));
		CarChartInfo carChartInfo1 = new CarChartInfo("东风悦达起亚K2",list1,40,30, 30,100);
		List<KeyVal> list2 = new ArrayList<KeyVal>();
		list2.add(new KeyVal("外观",1718d));  
		list2.add(new KeyVal("内饰",218d));  
		list2.add(new KeyVal("空间",218d));  
		list2.add(new KeyVal("操控",418d));  
		list2.add(new KeyVal("动力",1998d)); 
		list2.add(new KeyVal("油耗",156d)); 
		list2.add(new KeyVal("舒适性",279d)); 
		list2.add(new KeyVal("配置",470d));
		list2.add(new KeyVal("价格",31d));
		CarChartInfo carChartInfo2 = new CarChartInfo("东风悦达起亚",list2,50,10, 30,100);
		
//		export(carChartInfo1,carChartInfo2,2,2,"/Users/satuo20/demo/freechart/demo.jpg");
	}
	
//	public synchronized final static void export(CarChartInfo carChartInfo1,CarChartInfo carChartInfo2,int mytype,int othertype,String outputFile) {
//		export(carChartInfo1,carChartInfo2,mytype,othertype,outputFile);
//	}
	
	public synchronized final static void export (CarChartInfo carChartInfo1,CarChartInfo carChartInfo2,int mytype,int othertype,String outputFile) {
		FileOutputStream fos = null;
		
		try {
			JFreeChart barChart1 = getBarChart(carChartInfo1.getList());
			JFreeChart pieChart1 = getPie(mytype,carChartInfo1.gethValue()	,carChartInfo1.getmValue(),carChartInfo1.getlValue());
			JFreeChart barChart2 = getBarChart(carChartInfo2.getList());
			JFreeChart pieChart2 = getPie(othertype,carChartInfo2.gethValue()	,carChartInfo2.getmValue(),carChartInfo2.getlValue());
			
			int baseHeght = 300;
			int baseWidth = 300*3;
			int offset = 40;
			BufferedImage tmp = new BufferedImage(baseWidth+baseHeght, baseHeght*2+offset*4, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D)tmp.getGraphics();
			g2d.fillRect(0, 0, tmp.getWidth(), offset*2);
			
			g2d.drawImage(pieChart1.createBufferedImage(baseHeght, baseHeght), 0, offset*2,baseHeght,baseHeght, null);
			g2d.drawImage(barChart1.createBufferedImage(baseWidth, baseHeght), baseHeght, offset*2,baseWidth,baseHeght, null);
			
			g2d.fillRect(0, baseHeght+offset*2, tmp.getWidth(), offset*2);
			
			g2d.drawImage(pieChart2.createBufferedImage(baseHeght, baseHeght), 0, baseHeght+offset*4,baseHeght,baseHeght, null);
			g2d.drawImage(barChart2.createBufferedImage(baseWidth, baseHeght), baseHeght, baseHeght+offset*4,baseWidth,baseHeght, null);
			
			
			drawCarString(g2d,tmp,"关注车系",carChartInfo1,20,20,mytype);
			drawCarString(g2d,tmp,"对比车系",carChartInfo2,15,baseHeght+offset*2,othertype);
			File file = new File(outputFile);
			if(!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			file=new File(outputFile);
			fos = new FileOutputStream(file);
			ImageIO.write(tmp, "jpg", fos);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	private final static void drawCarString(Graphics2D g2d,BufferedImage baseBuffer,String title,CarChartInfo carChartInfo,int fromX,int fromY,int type) {
		//设置画笔颜色
		g2d.setColor(new Color(224,102,0));
		int fontSize = 18;
		g2d.setFont(new Font("宋体", Font.PLAIN, fontSize));
		g2d.drawString(title, fromX, fromY+10);
		
		int len = carChartInfo.getName().length()*fontSize;
		g2d.setColor(BAR_COLOR);
		g2d.fillRect(fromX+80, fromY-18, len+35, 36);
		g2d.setColor(Color.WHITE);
		g2d.drawString(carChartInfo.getName(), fromX+85, fromY+10);
		//画三角下拉菜单
		g2d.fillPolygon(new int[]{fromX+80+len+10,fromX+80+len+26,fromX+80+len+18},new int []{fromY-4,fromY-4,fromY+9},3);
		drawLine(g2d, 0, fromY+20, baseBuffer.getWidth(), fromY+20);
		
		
		int pjW = 80;
		int pjH = 33;
		int offset = 160;
		boolean raised = true;
		Color normal = new Color(237,237,237);
		g2d.setColor(type == 1?BAR_COLOR:normal);
		g2d.fill3DRect(baseBuffer.getWidth() - offset, fromY+40, pjW, pjH,raised);
		g2d.setColor(type==1?Color.WHITE:Color.BLACK);
		g2d.drawString("好评", baseBuffer.getWidth() - 140, fromY+62);
		
		g2d.setColor(type==2?BAR_COLOR:normal);
		g2d.fill3DRect(baseBuffer.getWidth() - offset, fromY+73, pjW, pjH,raised);
		g2d.setColor(type==2?Color.WHITE:Color.BLACK);
		g2d.drawString("中评", baseBuffer.getWidth() - 140, fromY+96);
		
		g2d.setColor(type==3?BAR_COLOR:normal);
		g2d.fill3DRect(baseBuffer.getWidth() - offset, fromY+106, pjW, pjH,raised);
		g2d.setColor(type==3?Color.WHITE:Color.BLACK);
		g2d.drawString("差评", baseBuffer.getWidth() - 140, fromY+128);
		
		int totalCount = (int)carChartInfo.tValue;
		int w = baseBuffer.getWidth()/2-80;
		g2d.setColor(new Color(223,97,0));
		g2d.drawString("评论总数："+totalCount, w, fromY+40);
		int newFontSize = 16;
		g2d.setFont(new Font("宋体", Font.PLAIN, newFontSize));
		g2d.setColor(Color.GRAY);
		g2d.drawString("(好评数:"+(int)carChartInfo.hValue+" 中评数:"+(int)carChartInfo.getmValue()+" 差评数:"+(int)carChartInfo.getlValue()+")", w-50, fromY+60);
	}
	
	private final static void drawLine(Graphics2D g2d,int x,int y,int w,int h) {
		g2d.setColor(Color.GRAY);
		g2d.setStroke(new BasicStroke(1.5f));
		g2d.drawLine(x,y,w,h);
		g2d.setColor(Color.WHITE);
	}
	
	/**
	 * @return
	 * @throws IOException
	 */
	//柱状图
	private final static JFreeChart getBarChart(List<KeyVal> list) throws IOException {
//		Collections.sort(list);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
		for(KeyVal kv:list) {
			dataset.addValue(kv.getValue(), "sz", kv.getKey());
		}
		JFreeChart chart = ChartFactory.createBarChart("",   
		                  "",  
		                  "",  
		                  dataset,  
		                  PlotOrientation.VERTICAL,  
		                  false,  
		                  false,  
		                  false);
		CategoryPlot plot = chart.getCategoryPlot();  
		plot.setBackgroundAlpha(0.0f);
		plot.getRangeAxis().setVisible(false);//隐藏Y轴
		plot.setOutlinePaint(Color.WHITE);//外边框颜色
		plot.getDomainAxis().setLabelFont(new Font("宋体", Font.PLAIN, 14)); 
		plot.getDomainAxis().setTickLabelFont(new Font("宋体", Font.PLAIN, 14)); 

		//设置网格背景颜色  
//		plot.setBackgroundPaint(Color.white);  
		//设置网格竖线颜色  new Color(70, 124, 185)
//		plot.setDomainGridlinePaint(Color.pink);  
		//设置网格横线颜色  
//		plot.setRangeGridlinePaint(Color.pink); 
		
		//显示每个柱的数值，并修改该数值的字体属性  
		BarRenderer renderer = new BarRenderer();
		plot.setRenderer(renderer); //新建一个BarRenderer并添加到PLOT中
		
		renderer.setMaximumBarWidth(0.025);
		//renderer设置
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());  
		renderer.setBaseItemLabelsVisible(true);  
		  
		//默认的数字显示在柱子中，通过如下两句可调整数字的显示  
		//注意：此句很关键，若无此句，那数字的显示会被覆盖，给人数字没有显示出来的问题  
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));  
		renderer.setItemLabelAnchorOffset(5D); 
		
		//设置每个地区所包含的平行柱的之间距离  
		renderer.setItemMargin(0.4);  
		
		//柱子颜色设置
		renderer.setSeriesPaint(0, BAR_COLOR);
		renderer.setShadowVisible(false);//隐藏阴影
		return chart;
		
//		ChartUtilities.saveChartAsJPEG(new File("/Users/satuo20/demo/freechart/a.jpg"),1.0f, chart, 800, 400);
	}
	
	//饼状图
	private final static  JFreeChart getPie(int type,double hValue,double mValue,double lValue) throws IOException {
		DefaultPieDataset dataset = new DefaultPieDataset(); 
		dataset.setValue("好评率",hValue); 
		dataset.setValue("差评率",lValue); 
		dataset.setValue("中评率",mValue);
		
		JFreeChart chart = ChartFactory.createPieChart("", dataset, true, false, false);
		PiePlot plot =  (PiePlot)chart.getPlot();
		plot.setLabelFont(new Font("宋体", Font.PLAIN, 14)); 
		//背景透明加白色
		plot.setBackgroundAlpha(0.0f);
		plot.setOutlinePaint(Color.WHITE);//外边框颜色
		
		plot.setNoDataMessage("没有数据");
		
		//开始角度（默认＝90）
		plot.setStartAngle(135);
		
		//设置各块的颜色
		plot.setSectionPaint("好评率", new Color(255,0,0));
		plot.setSectionPaint("中评率", new Color(70,124,185));
		plot.setSectionPaint("差评率", new Color(111,194,48));
		
		//突出显示|选中状态
		plot.setExplodePercent(type==1?"好评率":type==2?"中评率":"差评率", 0.1D);
		
		chart.getLegend().visible = false;
		
		return chart;
		
	}
	
	public static class KeyVal implements Comparable<KeyVal> {
		private String key;
		private Number value;
		public KeyVal(String key, Number value) {
			super();
			this.key = key;
			this.value = value;
		}
		public String getKey() {
			return key;
		}
		public Number getValue() {
			return value;
		}
		
		public int compareTo(KeyVal o) {
			return (o.value.doubleValue() - this.value.doubleValue())>0?1:0;
		}
	}
	
	public static class CarChartInfo {
		private List<KeyVal> list;
		private double hValue;
		private double mValue;
		private double lValue;
		private double tValue;
		private String name;
		
		public CarChartInfo( String name,List<KeyVal> list, double hValue, double mValue,
				double lValue,double tValue) {
			super();
			this.list = list;
			this.hValue = hValue;
			this.mValue = mValue;
			this.lValue = lValue;
			this.tValue = tValue;
			this.name = name;
		}
		public List<KeyVal> getList() {
			return list;
		}
		public void setList(List<KeyVal> list) {
			this.list = list;
		}
		public double gethValue() {
			return hValue;
		}
		public void sethValue(double hValue) {
			this.hValue = hValue;
		}
		public double getmValue() {
			return mValue;
		}
		public void setmValue(double mValue) {
			this.mValue = mValue;
		}
		public double getlValue() {
			return lValue;
		}
		public void setlValue(double lValue) {
			this.lValue = lValue;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double gettValue() {
			return tValue;
		}
		public void settValue(double tValue) {
			this.tValue = tValue;
		}
		
		
	}
}
