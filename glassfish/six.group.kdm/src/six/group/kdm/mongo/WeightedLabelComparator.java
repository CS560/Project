package six.group.kdm.mongo;

import java.util.Comparator;

public class WeightedLabelComparator implements Comparator<WeightedLabel> {

	@Override
	public int compare(WeightedLabel o1, WeightedLabel o2) {
		if (o1.weight > o2.weight)
			return -1;
		else if (o1.weight == o2.weight)
			return 0;
		else
			return 1;
	}
}
