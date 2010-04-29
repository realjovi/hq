package org.hyperic.hq.escalation.server.session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hyperic.hq.context.Bootstrap;
import org.springframework.dao.OptimisticLockingFailureException;

/**
 * This class actually performs the execution of a given segment of an
 * escalation. These are queued up and run by the executor thread pool.
 */
public class EscalationRunner implements Runnable {
    
    private Integer stateId;
    private EscalationRuntime escalationRuntime = Bootstrap.getBean(EscalationRuntime.class);
    private final Log log = LogFactory.getLog(EscalationRunner.class);
    
    public EscalationRunner(Integer stateId) {
        this.stateId = stateId;
    }

    public void run() {
        int maxRetries = 3;
        for (int i=0; i<maxRetries; i++) {
            try {
                escalationRuntime.executeState(stateId);
                break;
            }catch(OptimisticLockingFailureException e) {
                if ((i+1) < maxRetries) {
                    String times = (maxRetries - i == 1) ? "time" : "times";
                    log.warn("Warning, exception occurred while running escalation.  will retry "
                        + (maxRetries - (i+1)) + " more " + times + ".  errorMsg: " + e);
                    continue;
                }
                log.error("Exception occurred, runEscalation() will not be retried",e);
                break;
            } catch(Throwable t) {
                log.error("Exception occurred, runEscalation() will not be retried",t);
                break;
            }
        }
    }
}