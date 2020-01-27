package brown.auction.type.distribution.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.auction.type.distribution.ITypeDistribution;
import brown.auction.type.generator.ITypeGenerator;
import brown.auction.type.valuation.IType;
import brown.auction.type.valuation.library.BoSIIType;

public class BosIITypeDistribution extends AbsTypeDistribution implements ITypeDistribution {
  private List<List<Integer>> agentGroups;
  private Map<Integer, Integer> types;
  
  private static final Integer ROW = 0, COL = 1;
  private static final Integer GOOD = 0, BAD = 1, NEUTRAL = null;
	
  public BosIITypeDistribution(List<ITypeGenerator> generators) {
    super(generators);
    this.agentGroups = null;
    this.types = new HashMap<>();
  }

  @Override
  public IType sample(Integer agentID, List<List<Integer>> groups) {
	update(groups);
	if (this.types.get(agentID).intValue() == ROW) {
		return new BoSIIType(NEUTRAL);
	} else {
		if (Math.random() < 1.0 / 3) {
			return new BoSIIType(BAD);
		} else {
			return new BoSIIType(GOOD);
		}
	}
  }
  
  public synchronized void update(List<List<Integer>> groups) {
	  if (this.agentGroups == null || !groups.equals(this.agentGroups)) {
		  this.agentGroups = groups;
		  
		  this.types.clear();
		  for (List<Integer> group : this.agentGroups) {
			  if (group.size() != 2) {
				  for (Integer agent : group) {
					  this.types.put(agent, COL);
				  }
			  } else {
				  if (Math.random() < 0.5) {
					  this.types.put(group.get(0), ROW);
					  this.types.put(group.get(1), COL);
				  } else {
					  this.types.put(group.get(1), ROW);
					  this.types.put(group.get(0), COL);
				  }
			  }
		  }
	  }
  }

}
