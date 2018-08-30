package model;


import java.util.Comparator;

public class DistanceComparator implements Comparator<Star>{
	@Override
	public int compare(Star s1, Star s2) {
		return s1.getDistanceBranch() < s2.getDistanceBranch() ? -1 : s1.getDistanceBranch() == s2.getDistanceBranch() ? 0:1;
	}
}
