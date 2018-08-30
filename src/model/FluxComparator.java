package model;

import java.util.Comparator;

public class FluxComparator implements Comparator<Star>{
	@Override
	public int compare(Star s1, Star s2) {
		return s1.getFlux() < s2.getFlux() ? -1 : s1.getFlux() == s2.getFlux() ? 0:1;
	}
}
