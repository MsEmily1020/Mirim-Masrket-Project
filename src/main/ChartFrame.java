package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import base.BaseFrame;

public class ChartFrame extends BaseFrame {

	public ChartFrame() {
		super("차트", 1000, 700);

        // 차트 데이터 생성
		DefaultPieDataset dataset = createDataset();

        // 원형 차트 생성
        JFreeChart chart = ChartFactory.createPieChart(
                "Top Categories Chart",  // 차트 제목
                dataset,  // 데이터셋
                true,     // 레전드 표시 여부
                true,
                false);

        chart.getTitle().setFont(new Font("굴림", Font.BOLD, 30));
        chart.getLegend().setItemFont(new Font("굴림", Font.BOLD, 20));
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("굴림", Font.BOLD, 20));
        plot.setBackgroundPaint(Color.white);
        
        // 차트 패널 생성 및 추가
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(560, 370));
        setContentPane(chartPanel);    }

    private DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // 데이터베이스 연결 및 조회
        try {
        	var rs = getResult("select category, sum(1) as sum from post group by no");

            // 결과셋에서 데이터 읽어오기
            while (rs.next()) {
            	var rs1 = getResult("select * from category where no = ?", rs.getInt("category"));
            	rs1.next();
                String category = rs1.getString("name");
                int value = rs.getInt("sum");
                dataset.setValue(category, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataset;
    }

	public static void main(String[] args) {
		new ChartFrame().setVisible(true);
	}
}
