package cc;
import java.io.*;

import weka.attributeSelection.ChiSquaredAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.*;
import weka.clusterers.*;



import weka.classifiers.Classifier;
import weka.core.converters.ConverterUtils.DataSink;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.Evaluation;
import weka.experiment.ClassifierSplitEvaluator;
import weka.filters.*;
import weka.filters.unsupervised.attribute.Remove;
import weka.core.Instances;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import weka.core.Utils;


import weka.classifiers.*;

import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.core.Utils;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

import weka.clusterers.ClusterEvaluation;

import java.util.Random;

import weka.filters.Filter;
import weka.filters.supervised.attribute.AddClassification;
import weka.filters.unsupervised.attribute.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class c {
  
	/**
	 * @param args
	 */
	public static void m1() throws Exception{
		int[] a={150,1000,200,400,3000};
		for (int i = 0; i < 1; i++) {
			Instances data = new Instances(new BufferedReader(new FileReader(
					"D:/LZX/colon.arff")));
			data.setClassIndex(data.numAttributes() - 1);

			// attribute selection
			weka.filters.supervised.attribute.AttributeSelection filterattribute = new weka.filters.supervised.attribute.AttributeSelection();
			ChiSquaredAttributeEval evalfilter = new ChiSquaredAttributeEval();
			
			Ranker search = new Ranker();
			search.setNumToSelect(a[i]);

			filterattribute.setEvaluator(evalfilter);
			filterattribute.setSearch(search);
			filterattribute.setInputFormat(data);

			SimpleDateFormat sdf = new SimpleDateFormat("",
					Locale.SIMPLIFIED_CHINESE);
			sdf.applyPattern("yyyy年MM月dd日HH时mm分ss秒");
			String timeStr2 = sdf.format(new Date());
			System.out.println(timeStr2);

			Instances newData = Filter.useFilter(data, filterattribute);

			System.out.println("filter finished!");
			// clusterer
			
			SelfOrganizingMap clu = new SelfOrganizingMap();
			clu.setWidth(1);
			clu.setHeight(2);

			int seed = 14;
			int folds = 5;			

			// perform cross-validation
			System.out.println();
			System.out.println("=== Setup ===");
			System.out.println("Clusterer: " + clu.getClass().getName() + " "
					+ Utils.joinOptions(clu.getOptions()));
			System.out.println("Dataset: " + data.relationName());
			System.out.println("Folds: " + folds);
			System.out.println("Seed: " + seed);
			System.out.println();

			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(
						new FileWriter("d:/lzx/experiment4/colon+SelfOrganizingMap"+a[i]+"m.txt")));
				String timeStr = sdf.format(new Date());
				out.println(timeStr);
				System.out.println(timeStr);
				out.println(newData.toString());

				for (int n = 0; n < folds; n++) {
					System.out.println("fold:" + (n + 1));
					out.println("fold:" + (n + 1));
					ClusterEvaluation eval = new ClusterEvaluation();
					Instances train = newData.trainCV(folds, n);
					Instances test = newData.testCV(folds, n);

					// class to cluster
					Remove filter = new Remove();
					filter.setAttributeIndices("" + (train.classIndex() + 1));
					filter.setInputFormat(train);
					Instances dataClusterer = Filter.useFilter(train, filter);

					// build and evaluate cluster
					Clusterer cc = AbstractClusterer.makeCopy(clu);
					cc.buildClusterer(dataClusterer);
					eval.setClusterer(cc);
					eval.evaluateClusterer(test);

					out.println();
					out.println(eval.clusterResultsToString());

				}
				String timeStr1 = sdf.format(new Date());
				out.println(timeStr1);
				System.out.println(timeStr1);
				out.close();
			} catch (Exception e) {

			}
		}
		// output evaluation
		System.out.println("finished!");
		}
	
	
	public static void m2() throws Exception {
		
		int[] a={150,1000,2000,4000,6000};
		for (int i = 0; i < 1; i++) {
			Instances data = new Instances(new BufferedReader(new FileReader(
					"D:/LZX/leukemia_train.arff")));
			data.setClassIndex(data.numAttributes() - 1);
			Instances data1 = new Instances(new BufferedReader(new FileReader(
					"D:/LZX/leukemia_test.arff")));

			// attribute selection
			weka.filters.supervised.attribute.AttributeSelection filterattribute = new weka.filters.supervised.attribute.AttributeSelection();
			ChiSquaredAttributeEval evalfilter = new ChiSquaredAttributeEval();
			Ranker search = new Ranker();
			search.setNumToSelect(a[i]);

			filterattribute.setEvaluator(evalfilter);
			filterattribute.setSearch(search);
			filterattribute.setInputFormat(data);

			SimpleDateFormat sdf = new SimpleDateFormat("",
					Locale.SIMPLIFIED_CHINESE);
			sdf.applyPattern("yyyy年MM月dd日HH时mm分ss秒");
			String timeStr2 = sdf.format(new Date());
			System.out.println(timeStr2);

			Instances newData = Filter.useFilter(data, filterattribute);
			Instances newData1 = Filter.useFilter(data1, filterattribute);
			System.out.println("filter finished!");
			// clusterer
			SelfOrganizingMap clu = new SelfOrganizingMap();
			clu.setWidth(1);
			clu.setHeight(2);

			// other options
			int seed = 14;
			int folds = 5;			
			// perform cross-validation
			System.out.println();
			System.out.println("=== Setup ===");
			System.out.println("Clusterer: " + clu.getClass().getName() + " "
					+ Utils.joinOptions(clu.getOptions()));
			System.out.println("Dataset: " + data.relationName());
			System.out.println("Folds: " + folds);
			System.out.println("Seed: " + seed);
			System.out.println();

			try {
				PrintWriter out = new PrintWriter(new BufferedWriter(
						new FileWriter("d:/lzx/experiment5/leukemia+SelfOrganizingMap" + a[i]
								+ "m.txt")));
				String timeStr = sdf.format(new Date());
				out.println(timeStr);
				System.out.println(timeStr);
				out.println(newData.toString());

				ClusterEvaluation eval = new ClusterEvaluation();

				// class to cluster
				Remove filter = new Remove();
				filter.setAttributeIndices("" + (newData.classIndex() + 1));
				filter.setInputFormat(newData);
				Instances dataClusterer = Filter.useFilter(newData, filter);

				// build and evaluate cluster
				Clusterer cc = AbstractClusterer.makeCopy(clu);
				cc.buildClusterer(dataClusterer);
				eval.setClusterer(cc);
				eval.evaluateClusterer(newData1);

				out.println();
				out.println(eval.clusterResultsToString());

				String timeStr1 = sdf.format(new Date());
				out.println(timeStr1);
				System.out.println(timeStr1);
				out.close();
			} catch (Exception e) {

			}

			// output evaluation
			System.out.println("finished!");
			
		}
	}
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		m1();
		m2();	
	}

}
