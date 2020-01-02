package brown.platform.game.library; 

 import brown.auction.rules.IActivityRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.auction.rules.IInnerIRPolicy;
import brown.auction.rules.IQueryRule;
import brown.auction.rules.ITerminationCondition;
import brown.auction.rules.IUtilityRule;
import brown.platform.game.IFlexibleRules;  
 
/**
 * Used to group a set of market rules together. 
 * @author andrewcoggins
 *
 */
 public class FlexibleRules extends AbsGameRules implements IFlexibleRules {  
   
   /**
    * Consists of one of each of the market rules. 
    * @param aRule
    * @param pRule
    * @param qRule
    * @param actRule
    * @param irPolicy
    * @param innerIRPolicy
    * @param tCondition
    */
    public FlexibleRules(IUtilityRule aRule,  
                         IQueryRule qRule,  
                         IActivityRule actRule, 
                         IInformationRevelationPolicy irPolicy, 
                         IInnerIRPolicy innerIRPolicy, 
                         ITerminationCondition tCondition  
                         ) {  
        super(aRule, qRule, actRule, irPolicy, innerIRPolicy, tCondition);  
    } 
    
     public AbsGameRules copy() { 
        return new FlexibleRules(aRule,qRule, actRule, infoPolicy, innerIRPolicy, tCondition);  
    } 
}