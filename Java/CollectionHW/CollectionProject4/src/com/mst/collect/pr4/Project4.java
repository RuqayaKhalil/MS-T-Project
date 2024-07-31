package com.mst.collect.pr4;

import java.util.ArrayList;
import java.util.List;

public class Project4 {
	
	public static List<String> findMaxStrs(List<String> arrayList){
		List<String> helperArrList = new ArrayList<>();
		String maxStrInLength = arrayList.get(0); 
		//int maxLength = helperArrList.stream().mapToInt(String::length).max().orElse(0);
		int maxLength = maxStrInLength.length(); 
		for(String element : arrayList) {
			if(element.length() > maxStrInLength.length()) {
				maxLength = element.length();
			}
		}
		
		for(String element : arrayList) {
			if(element.length() == maxLength) {
				helperArrList.add(element);
			}
		}
		return helperArrList;
	}

	public static void main(String[] args) {
		List<String> arrayList = new ArrayList<>();
		for (int i=0; i<5;i++) {
			arrayList.add(args[i]);
		}
		System.out.println(findMaxStrs(arrayList));
	}

}
