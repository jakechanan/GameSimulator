package brown.platform.market.library;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import brown.auction.rules.IActivityRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IInnerIRPolicy;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.auction.rules.IUtilityRule;
import brown.platform.game.IGameRules;
import brown.platform.game.library.FlexibleRules;

public class FlexibleRulesTest {

  @Test
  public void testFlexibleRules() {
    
    IUtilityRule mockAllocationRule = mock(IUtilityRule.class); 
    IQueryRule mockQueryRule = mock(IQueryRule.class);
    IActivityRule mockActivityRule = mock(IActivityRule.class); 
    IInformationRevelationPolicy mockIR = mock(IInformationRevelationPolicy.class); 
    ITerminationCondition mocktCondition = mock(ITerminationCondition.class); 
    IInnerIRPolicy innerIR = mock(IInnerIRPolicy.class); 
    
    IGameRules mRules = new FlexibleRules(mockAllocationRule, mockQueryRule, mockActivityRule, mockIR, innerIR, mocktCondition);
    
    assertEquals(mRules.getARule(), mockAllocationRule); 
    
    assertEquals(mRules.getQRule(), mockQueryRule);
    assertEquals(mRules.getActRule(), mockActivityRule);
    assertEquals(mRules.getIRPolicy(), mockIR);
    assertEquals(mRules.getTerminationCondition(), mocktCondition);
    
  }
}
