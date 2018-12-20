package com.qingyang.test;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author qingyang
 * @date 2018/9/13.
 */
public class ListUtil {

	public static <T> List<T> removeDup(Function<T, String> classifier, List<T> srcList, String hundredPercentMatchedKey) {
		Map<String, List<T>> groupParts = srcList.stream().collect(Collectors.groupingBy(classifier));
		List<T> result = Lists.newArrayList();
		if(groupParts.get(hundredPercentMatchedKey) != null){
			for(Map.Entry<String, List<T>> e : groupParts.entrySet()){
				if (e.getKey().equals(hundredPercentMatchedKey) && e.getValue().size() > 1) {
					result = Lists.newArrayList();
					break;
				} else if (e.getValue().size() == 1) {
					result.addAll(e.getValue());
				}
			}
		}else{
			for(Map.Entry<String, List<T>> e : groupParts.entrySet()){
				if(e.getValue().size() > 1){
					result = Lists.newArrayList();
					break;
				}else{
					result.addAll(e.getValue());
				}
			}
		}
		return result;
	}
}
